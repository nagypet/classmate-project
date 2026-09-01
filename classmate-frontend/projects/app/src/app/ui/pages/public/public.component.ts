/*
 * Copyright 2020-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ResponsiveClassDirective} from '../../../../../../ngface/src/lib/directives/responsive-class-directive';
import {FormBaseComponent} from '../../../../../../ngface/src/lib/form/form-base.component';
import {ChangeService} from '../../../core/services/change.service';


@Component({
  selector: 'app-public',
  standalone: true,
  imports: [
    ResponsiveClassDirective,
  ],
  templateUrl: './public.component.html',
  styleUrl: './public.component.scss',
})
export class PublicComponent extends FormBaseComponent implements OnInit, OnDestroy
{

  constructor(
    private changeService: ChangeService
  )
  {
    super();
  }

  ngOnInit(): void
  {
    this.changeService.triggerReload(this);
  }


  ngOnDestroy(): void
  {
    //this.sseChannel.close();
  }
}
