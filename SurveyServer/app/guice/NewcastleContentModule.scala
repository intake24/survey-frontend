package guice

import com.google.inject.AbstractModule

class NewcastleContentModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CustomContent]).to(classOf[NewcastleCustomContent])
  }
}
