package guice

import com.google.inject.AbstractModule

class MRCContentModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CustomContent]).to(classOf[MRCCustomContent])
  }
}
