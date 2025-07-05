import { WebPlugin } from '@capacitor/core';

import type { SocialAuthOptions, SocialAuthPlugin, SocialAuthResult } from './definitions';


export class SocialAuthWeb extends WebPlugin implements SocialAuthPlugin {
  async signIn(options: SocialAuthOptions): Promise<SocialAuthResult> {
    console.log('SocialAuthWeb.signIn', options);

    // Web implementation would use Google Identity Services
    // For now, return mock data
    return {
      idToken: 'mock-id-token',
      email: 'user@example.com',
      displayName: 'Mock User',
      familyName: 'User',
      givenName: 'Mock',
      profilePictureUri: 'https://example.com/avatar.jpg',
    };
  }


  async signOut(): Promise<void> {
    console.log('SocialAuthWeb.signOut');
    // Web implementation would clear Google Identity Services state
  }
}
