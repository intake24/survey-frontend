package filters

import javax.inject.Inject
import play.api.http.DefaultHttpFilters

class Intake24ApiFilters @Inject() (init: Intake24ApiInit, corsFilter: CorsFilter) extends DefaultHttpFilters(corsFilter)