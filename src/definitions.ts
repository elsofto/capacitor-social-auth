export interface SocialAuthPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
