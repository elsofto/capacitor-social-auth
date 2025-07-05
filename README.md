# capacitor-social-auth

Capacitor Social Auth Plugin

## Install

```bash
npm install capacitor-social-auth
npx cap sync
```

## API

<docgen-index>

* [`signIn(...)`](#signin)
* [`signOut()`](#signout)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### signIn(...)

```typescript
signIn(options: SocialAuthOptions) => Promise<SocialAuthResult>
```

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#socialauthoptions">SocialAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#socialauthresult">SocialAuthResult</a>&gt;</code>

--------------------


### signOut()

```typescript
signOut() => Promise<void>
```

--------------------


### Interfaces


#### SocialAuthResult

| Prop              | Type                |
| ----------------- | ------------------- |
| **`idToken`**     | <code>string</code> |
| **`id`**          | <code>string</code> |
| **`email`**       | <code>string</code> |
| **`displayName`** | <code>string</code> |
| **`givenName`**   | <code>string</code> |
| **`familyName`**  | <code>string</code> |
| **`photoUrl`**    | <code>string</code> |


#### SocialAuthOptions

| Prop                 | Type                                                              |
| -------------------- | ----------------------------------------------------------------- |
| **`provider`**       | <code><a href="#socialauthprovider">SocialAuthProvider</a></code> |
| **`clientId`**       | <code>string</code>                                               |
| **`serverClientId`** | <code>string</code>                                               |
| **`scopes`**         | <code>string[]</code>                                             |


### Enums


#### SocialAuthProvider

| Members      | Value                 |
| ------------ | --------------------- |
| **`GOOGLE`** | <code>'google'</code> |
| **`APPLE`**  | <code>'apple'</code>  |

</docgen-api>
