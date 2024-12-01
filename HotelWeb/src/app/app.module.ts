import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {DemoNgZorroAndModule} from './DemoNgZorroAndModule';
import {en_US, NZ_I18N} from 'ng-zorro-antd/i18n';
import {routes} from './app.routes';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [

  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    DemoNgZorroAndModule,
    AppComponent,
    ReactiveFormsModule,
    RouterModule.forRoot(routes)  // Register routing here
  ],
  providers: [
    {provide:NZ_I18N,useValue:en_US}
  ],
  bootstrap: []
})
export class AppModule {}
