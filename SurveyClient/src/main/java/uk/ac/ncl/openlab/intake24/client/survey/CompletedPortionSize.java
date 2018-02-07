/*
This file is part of Intake24.

Copyright 2015, 2016 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import uk.ac.ncl.openlab.intake24.client.BrowserConsole;
import uk.ac.ncl.openlab.intake24.client.api.errors.ErrorReportingService;

import java.util.HashMap;
import java.util.Map;

public class CompletedPortionSize {
    public String method;
    public Map<String, String> data;

    @Deprecated
    public CompletedPortionSize() {

    }

    public CompletedPortionSize(String method, Map<String, String> data) {
        this.method = method;
        this.data = data;
    }

    public double servingWeight() {
        String str = data.get("servingWeight");

        if (str == null)
            return 0;
        else
            return Double.parseDouble(str);
    }

    public double leftoversWeight() {
        String str = data.get("leftoversWeight");
        if (str == null)
            return 0;
        else
            return Double.parseDouble(str);
    }

    public CompletedPortionSize multiply(double x) {
        HashMap<String, String> newData = new HashMap<String, String>(data);

        newData.put("servingWeight", Double.toString(servingWeight() * x));
        newData.put("leftoversWeight", Double.toString(leftoversWeight() * x));

        return new CompletedPortionSize(method, newData);
    }

    public static CompletedPortionSize ignore(String reason) {
        Map<String, String> data = new HashMap<String, String>();

        data.put("reason", reason);
        data.put("servingWeight", "0.0");
        data.put("leftoversWeight", "0.0");

        return new CompletedPortionSize("ignored", data);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompletedPortionSize other = (CompletedPortionSize) obj;

        // System.out.println("CPS begin");

        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;

        // System.out.println("CPS data ok");

        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.equals(other.method))
            return false;

        // System.out.println("CPS script OK");

        return true;
    }

}
