import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthModule } from './auth/auth.module';
import {NgxWebstorageModule} from 'ngx-webstorage';

import {BrowserAnimationsModule} from '@Angular/platform-browser/animations';

import { ToastrModule } from 'ngx-toastr';
import { TockenInterceptor } from './tocken.interceptor';
import { HomeComponent } from './home/home.component';
import { PostTileComponent } from './shared/post-tile/post-tile.component';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';
import { VoteButtonComponent } from './shared/vote-button/vote-button.component';
import { SideBarComponent } from './shared/side-bar/side-bar.component';
import { PjblogSideBarComponent } from './shared/pjblog-side-bar/pjblog-side-bar.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CreatePjblogComponent } from './pjblog/create-pjblog/create-pjblog.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { ListPjblogsComponent } from './pjblog/list-pjblogs/list-pjblogs.component';
import { EditorModule } from '@tinymce/tinymce-angular';
import { ViewPostComponent } from './post/view-post/view-post.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserDisplayModelComponent } from './shared/user-display-model/user-display-model.component';
import {WebcamModule} from 'ngx-webcam';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    PostTileComponent,
    VoteButtonComponent,
    SideBarComponent,
    PjblogSideBarComponent,
    CreatePjblogComponent,
    CreatePostComponent,
    ListPjblogsComponent,
    ViewPostComponent,
    UserProfileComponent,
    UserDisplayModelComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    AuthModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FontAwesomeModule ,
    EditorModule,
    NgbModule,
    WebcamModule
   ],
  providers: [
    {provide: HTTP_INTERCEPTORS,
      useClass: TockenInterceptor,
      multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
