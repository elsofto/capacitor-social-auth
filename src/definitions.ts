export enum SocialAuthProvider {
  GOOGLE = 'google',
  APPLE = 'apple'
}


export interface SocialAuthOptions {
  provider: SocialAuthProvider;
  clientId: string;
  serverClientId: string;
  scopes: string[];
}


export interface SocialAuthResult {
  idToken: string;
  id: string;
}


export interface SocialAuthPlugin {
  signIn(options: SocialAuthOptions): Promise<SocialAuthResult>;

  signOut(): Promise<void>;
}
