import { registerPlugin } from '@capacitor/core';

import type { SocialAuthPlugin } from './definitions';

const SocialAuth = registerPlugin<SocialAuthPlugin>('SocialAuth', {
  web: () => import('./web').then((m) => new m.SocialAuthWeb()),
});

export * from './definitions';
export { SocialAuth };
