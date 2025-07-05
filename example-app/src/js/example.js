import { SocialAuth, SocialAuthProvider } from 'capacitor-social-auth';

window.testSign = async () => {
  try {
    console.log('Starting Google Sign-In...');
    const res = await SocialAuth.signIn({
      provider: SocialAuthProvider.GOOGLE,
      clientId: '228029454374-055o8nb9g9tvkj2jcip4sa9mem178f91.apps.googleusercontent.com',
      serverClientId: '228029454374-055o8nb9g9tvkj2jcip4sa9mem178f91.apps.googleusercontent.com',
      // serverClientId: '228029454374-r0d03bfonjd5emhnvp8qgjj7r5r3besn.apps.googleusercontent.com',
      scopes: [ 'email', 'profile' ],
    });
    console.log('Sign-In successful:', res);

    // Display user info
    document.getElementById('user-info').innerHTML = `
      <h3>Welcome, ${res.displayName}!</h3>
      <p>Email: ${res.email}</p>
      <p>ID Token: ${res.idToken.substring(0, 50)}...</p>
    `;
  } catch (error) {
    console.error('Sign-In failed:', error);
    document.getElementById('user-info').innerHTML = `
      <h3>Sign-In Failed</h3>
      <p>Error: ${error.message}</p>
    `;
  }
};

window.testSignOut = async () => {
  try {
    console.log('Starting Google Sign-Out...');
    await SocialAuth.signOut();
    console.log('Sign-Out successful');
    document.getElementById('user-info').innerHTML = '<h3>Signed Out</h3>';
  } catch (error) {
    console.error('Sign-Out failed:', error);
    document.getElementById('user-info').innerHTML = `
      <h3>Sign-Out Failed</h3>
      <p>Error: ${error.message}</p>
    `;
  }
};
