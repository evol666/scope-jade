import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JadeSharedModule } from 'app/shared/shared.module';
import { JadeCoreModule } from 'app/core/core.module';
import { JadeAppRoutingModule } from './app-routing.module';
import { JadeHomeModule } from './home/home.module';
import { JadeEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JadeSharedModule,
    JadeCoreModule,
    JadeHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JadeEntityModule,
    JadeAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class JadeAppModule {}
