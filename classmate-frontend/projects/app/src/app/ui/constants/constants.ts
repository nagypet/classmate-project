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

import {animate, state, style, transition} from "@angular/animations";

export const Tooltipanimation = [
  state('open', style({
    opacity: 1,
  })),
  state('closed', style({
    opacity: 0,
    height: 0
  })),
  transition('open => closed', [
    animate('0.3s')
  ]),
  transition('closed => open', [
    animate('0.1s')
  ]),
];

export const routes = {
  public: 'public',
  login: 'login',
  register: 'register',
} as const;
