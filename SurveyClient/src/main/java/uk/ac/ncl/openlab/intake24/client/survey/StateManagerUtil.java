/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
 */

package uk.ac.ncl.openlab.intake24.client.survey;

import com.google.gwt.core.client.GWT;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.EmbeddedData;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.api.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.json.SerialisableSurveyCodec;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SMeal;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SSelection;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SSurvey;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.sameasbefore.SSameAsBefore;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.sameasbefore.SerialisableSameAsBeforeCodec;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class StateManagerUtil {
    private static final Integer SERVER_SYNC_DELAY = 1500;
    private static Timer serverSyncTimer;

    final public static String LatestStateKeyPrefix = "survey-state-";
    final public static String SameAsBeforePrefix = "sab-";

    final public static Storage localStorage = Storage.getLocalStorageIfSupported();
    final public static Storage sessionStorage = Storage.getSessionStorageIfSupported();

    final public static Map<Integer, Survey> history = new TreeMap<Integer, Survey>();

    final public static SerialisableSameAsBeforeCodec sameAsBeforeCodec = GWT.create(SerialisableSameAsBeforeCodec.class);
    final public static SerialisableSurveyCodec surveyCodec = GWT.create(SerialisableSurveyCodec.class);

    final public static Logger log = Logger.getLogger(StateManagerUtil.class.getSimpleName());

    public static void setItem(String key, String data) {
        localStorage.setItem(key, data);
    }

    public static Option<String> getItem(String key) {
        String data = localStorage.getItem(key);

        if (data != null)
            return Option.some(data);
        else
            return Option.none();
    }

    public static String latestStateKey(String userName) {
        return LatestStateKeyPrefix + userName;
    }

    public static void clearLatestState(String userName) {
        localStorage.removeItem(latestStateKey(userName));
        cleanStateOnServer();
    }

    public static void clearHistory() {
        history.clear();
    }

    public static void setLatestState(String userName, Survey survey, String scheme_id, String version_id, Boolean storeOnServer) {
        String serialized = saveState(localStorage, latestStateKey(userName), survey, scheme_id, version_id);
        if (storeOnServer) {
            syncWithServer(serialized);
        }
    }

    public static Option<String> getLatestStateSerialised(String userName) {
        return Option.fromNullable(localStorage.getItem(latestStateKey(userName)));
    }

    public static Option<Survey> getLatestState(String userName, String scheme_id, String version_id,
                                                PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager) {
        return getSavedStateFromStorage(localStorage, latestStateKey(userName), scheme_id, version_id, scriptManager, templateManager);
    }

    public static void saveSameAsBefore(String userName, Meal meal, EncodedFood mainFood, String scheme_id,
                                        String version_id) {
        final String key = SameAsBeforePrefix + userName + "-" + mainFood.data.code;

        if (!mainFood.isEncoded())
            throw new IllegalArgumentException("Only non-compound foods are supported by same-as-before at this time");

        PVector<FoodEntry> linkedFoods = Meal.linkedFoods(meal.foods, mainFood);

        localStorage.setItem(key,
                sameAsBeforeCodec.encode(new SSameAsBefore(mainFood, linkedFoods, scheme_id, version_id))
                        .toString());
    }

    public static Option<SameAsBefore> getSameAsBefore(String foodCode, String scheme_id,
                                                       String version_id, PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager) {
        final String key = SameAsBeforePrefix + AuthCache.getCurrentUserId() + "-" + foodCode;

        final String serialised = localStorage.getItem(key);

        if (serialised == null)
            return Option.none();
        else
            try {
                SSameAsBefore decoded = sameAsBeforeCodec.decode(serialised);

                if (decoded.schemeId != scheme_id || decoded.versionId != version_id) {
                    log.warning("Version mismatch for same as before (" + foodCode + "): stored version is (" + decoded.schemeId
                            + ", " + decoded.versionId + "), runtime version is (" + scheme_id + ", " + version_id
                            + "). Ignoring record.");
                    return Option.none();
                } else
                    return Option.some(decoded.toSameAsBefore(scriptManager, templateManager));
            } catch (Throwable e) {
                e.printStackTrace();
                log.warning("Deserialisation failed for same as before: " + e.getClass()
                        .getName() + " (" + e.getMessage() + ")");
                return Option.none();
            }
    }

    public static void setHistoryState(String userName, int state_id, Survey survey) {
        // saveState(sessionStorage, historyKey(userName, state_id), survey);

        history.put(state_id, survey);
    }

    public static Option<Survey> getHistoryState(String userName, int state_id, PortionSizeScriptManager scriptManager) {
        if (history.containsKey(state_id))
            return Option.some(history.get(state_id));
        else
            return Option.none();
        // return getSavedState(sessionStorage, historyKey(userName, state_id),
        // scriptManager);
    }

    public static String saveState(Storage storage, String key, Survey survey, String scheme_id, String version_id) {
        // Logger log = Logger.getLogger("StateManager");
        String serialised = surveyCodec.encode(new SSurvey(survey, scheme_id, version_id))
                .toString();
        storage.setItem(key, serialised);
        return serialised;
        // log.info("Saved data for key \"" + key + "\":" + xml);
    }

    public static void getSavedStateFromServerOrStorage(String userName, String scheme_id, String version_id,
                                                        PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager,
                                                        MethodCallback<Option<Survey>> callback) {
        UserSessionService.INSTANCE.get(EmbeddedData.surveyId, new MethodCallback<UserSessionResponse>() {
            @Override
            public void onSuccess(Method method, UserSessionResponse userSessionResponse) {
                if (userSessionResponse.data.isEmpty()) {
                    callback.onSuccess(method, Option.none());
                } else {
                    callback.onSuccess(method, decodeSurvey(userSessionResponse.data.map(s -> s.sessionData).getOrDie(),
                            scheme_id, version_id, scriptManager, templateManager));
                }
            }

            @Override
            public void onFailure(Method method, Throwable throwable) {
                callback.onSuccess(method, getSavedStateFromStorage(localStorage, latestStateKey(userName), scheme_id, version_id, scriptManager, templateManager));
            }
        });
    }

    public static Option<Survey> getSavedStateFromStorage(Storage storage, String key, String scheme_id, String version_id,
                                                          PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager) {
        // Logger log = Logger.getLogger("StateManager");
        String data = storage.getItem(key);

        // log.info("Data for key \"" + key + "\":" + data);

        if (data == null)
            return Option.none();
        else {
            return decodeSurvey(data, scheme_id, version_id, scriptManager, templateManager);
        }
    }

    public static boolean isSelectionValid(SSurvey survey) {
        return survey.selectedElement.accept(new SSelection.Visitor<Boolean>() {

            @Override
            public Boolean visitMeal(SSelection.SSelectedMeal meal) {
                return (meal.mealIndex > 0 && meal.mealIndex < survey.meals.size());
            }

            @Override
            public Boolean visitFood(SSelection.SSelectedFood food) {
                if (food.mealIndex > 0 && food.mealIndex < survey.meals.size()) {
                    SMeal meal = survey.meals.get(food.mealIndex);
                    return (food.foodIndex > 0 && food.foodIndex < meal.foods.size());
                } else
                    return false;
            }

            @Override
            public Boolean visitNothing(SSelection.SEmptySelection selection) {
                return true;
            }
        });
    }

    public static Option<Survey> decodeSurvey(String data, String scheme_id, String version_id,
                                              PortionSizeScriptManager scriptManager,
                                              CompoundFoodTemplateManager templateManager) {
        try {
            SSurvey decoded = surveyCodec.decode(data);

            if (decoded.schemeId != scheme_id || decoded.versionId != version_id) {
                log.warning("Survey version mismatch: stored version is (" + decoded.schemeId + ", " + decoded.versionId
                        + "), runtime version is (" + scheme_id + ", " + version_id + "). Ignoring stored survey.");
                return Option.none();
            } else {
                if (!isSelectionValid(decoded)) {
                    log.warning("Selected element is not valid in the stored survey, replacing with empty selection");

                    SSurvey withEmptySelection = new SSurvey(decoded.meals,
                            new SSelection.SEmptySelection(SelectionMode.AUTO_SELECTION), decoded.startTime,
                            decoded.lastSaved, decoded.flags, decoded.customData, decoded.schemeId, decoded.versionId);

                    return Option.some(withEmptySelection.toSurvey(scriptManager, templateManager));
                } else
                    return Option.some(decoded.toSurvey(scriptManager, templateManager));
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.warning("Failed to parse saved survey state: " + e.getMessage() + "\n\n" + data);
            return Option.none();
        }
    }

    private static void syncWithServer(String serializedState) {
        if (serverSyncTimer != null) {
            serverSyncTimer.cancel();
        }
        serverSyncTimer = new Timer() {
            @Override
            public void run() {
                UserSessionService.INSTANCE.save(EmbeddedData.surveyId, new UserSessionRequest(serializedState),
                        new MethodCallback<UserSession>() {
                            @Override
                            public void onFailure(Method method, Throwable throwable) {
                                log.warning("Failed to saved latest state on server");
                            }

                            @Override
                            public void onSuccess(Method method, UserSession userSession) {
                                log.fine("Saved latest state on server");
                            }
                        });
            }
        };
        serverSyncTimer.schedule(SERVER_SYNC_DELAY);
    }

    private static void cleanStateOnServer() {
        if (serverSyncTimer != null) {
            serverSyncTimer.cancel();
        }
        UserSessionService.INSTANCE.clean(EmbeddedData.surveyId, new MethodCallback<UserSession>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                log.warning("Failed to clean user session from server");
            }

            @Override
            public void onSuccess(Method method, UserSession userSession) {
                log.fine("Cleaned user session on server");
            }
        });
    }
}
