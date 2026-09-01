import {Component, OnDestroy, OnInit} from '@angular/core';
import {Ngface} from '../../../../../../ngface/src/lib/ngface-models';
import {FormBaseComponent} from '../../../../../../ngface/src/lib/form/form-base.component';
import {Router} from '@angular/router';
import {TitlebarService} from '../../../core/services/titlebar.service';
import {
  NgfaceTitlebarComponent
} from '../../../../../../ngface/src/lib/titlebar/ngface-titlebar/ngface-titlebar.component';
import {ResponsiveClassDirective} from '../../../../../../ngface/src/lib/directives/responsive-class-directive';
import {environment} from '../../../../environments/environment';
import {ChangeService} from '../../../core/services/change.service';
import {filter, Subscription} from 'rxjs';
import {routes} from '../../constants/constants';
import {AuthService} from "../../../../../../ngface/src/lib/services/auth/auth.service";

@Component({
  selector: 'app-titlebar',
  standalone: true,
  imports: [
    NgfaceTitlebarComponent,
    ResponsiveClassDirective
  ],
  templateUrl: './titlebar.component.html',
  styleUrl: './titlebar.component.scss',
})
export class TitlebarComponent extends FormBaseComponent implements OnInit, OnDestroy
{
  appReady = false;

  private changeSubscription: Subscription;


  constructor(
    private router: Router,
    public authService: AuthService,
    private titlebarService: TitlebarService,
    private changeService: ChangeService,
  )
  {
    super();

    this.changeSubscription = this.changeService.change$
      .pipe(filter(v => v !== null))
      .subscribe(() => this.reloadTitlebar());
  }


  ngOnInit(): void
  {
    this.reloadTitlebar();
  }


  ngOnDestroy(): void
  {
    this.changeSubscription.unsubscribe();
  }


  private reloadTitlebar()
  {
    this.titlebarService.getTitlebar().subscribe(form =>
    {
      console.log(form);
      this.formData = form;
    });
  }


  onTitlebarMenuItemClick($event: Ngface.Menu.Item): void
  {
    this.router.navigate([$event.id]);
  }


  getSelectedMenuItemId()
  {
    return this.router.url.split('/')[1];
  }


  onTitlebarActionClick($event: Ngface.Action): void
  {
    console.log(`onTitlebarActionClick()`, $event.id);
    switch ($event.id)
    {
      case 'login':
        this.router.navigate([routes.login]);
        break;

      case 'logout':
        this.authService.logout().subscribe(() =>
        {
          this.reloadTitlebar();
          this.router.navigate([routes.public]);
        });
        break;
    }
  }


  getLogoUrl(): string
  {
    return `themes/${environment.theme}/company_logo.png`;
  }


  onTitlebarReady(): void
  {
    this.appReady = true;
  }
}
