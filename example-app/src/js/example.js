import { SocialAuth } from 'capacitor-social-auth';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    SocialAuth.echo({ value: inputValue })
}
