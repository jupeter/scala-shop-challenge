import javax.inject.Inject

import play.api.http.HttpFilters
import play.filters.cors.CORSFilter

/**
  * (c) Piotr Plenik <piotr.plenik@gmail.com>
  *
  * For the full copyright and license information, please view the LICENSE
  * file that was distributed with this source code.
  */
class Filters @Inject()(corsFilter: CORSFilter) extends HttpFilters {
  def filters = Seq(corsFilter)
}