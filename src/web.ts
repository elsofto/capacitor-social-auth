import { WebPlugin } from '@capacitor/core';

import type { SocialAuthPlugin } from './definitions';

export class SocialAuthWeb extends WebPlugin implements SocialAuthPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
