# Server requirements and live health checks

## Service matrix

| Service | Requirement | Endpoint / detection | Start or recover |
|---|---|---|---|
| Core Android UI and local features | Required; no mandatory remote server is established by the README | Android process and local storage checks | No server to start for local-only features. |
| Gemini | Optional cloud AI provider; required only when selected | Google Generative Language HTTPS API; use the configured model and key | Add the key in the supported settings flow or protected deployment configuration. Do not hard-code keys in the APK. |
| Groq | Optional cloud AI provider | Configured Groq OpenAI-compatible HTTPS endpoint | Add a valid key and model in the app's Free AI settings. |
| OpenRouter | Optional cloud AI provider | Configured OpenRouter HTTPS endpoint | Add a valid key and model in settings. Check provider/model availability. |
| Local OpenAI-compatible LLM | Optional alternative; requires a separately running local service | User-configured base URL, commonly a local Ollama endpoint at `http://127.0.0.1:11434/v1` if supported by the app's current configuration | Start the local provider on the same device or a reachable host, ensure the configured model is installed, and set the correct endpoint. |
| Firebase Auth / Firestore | Only if the active build enables the associated account/cloud features | Firebase project configuration plus a real sign-in and authorized database request | Configure the Firebase project, authentication provider and security rules in Firebase Console. Client config alone does not prove backend access works. |

## Health-state contract

- **HEALTHY**: live request succeeds for the selected feature and model.
- **DEGRADED**: endpoint responds but required model, auth provider or database permission is missing.
- **STOPPED**: local connection refused.
- **UNREACHABLE**: timeout/DNS/routing failure.
- **AUTH REQUIRED**: 401/403 or Firebase sign-in/permission failure.
- **NOT CONFIGURED**: optional service not configured.
- **UNVERIFIED**: no live probe has run.

Show checked-at time, configured provider, safe endpoint and last error. Never display secret values or mark a service healthy solely because an API key exists.

## Startup / recovery

- Cloud LLMs: configure the key and model, verify internet access, then run a live request.
- Local LLM: start Ollama (for example, `ollama serve` in Termux), verify `/api/version` and `/api/tags`, then test the configured model.
- Firebase: verify project configuration, enabled sign-in methods, Firestore rules and network access. Never loosen database rules just to make a health check pass.

## Truth boundary

This inventory reflects provider options described by the repository README and client configuration. It does not claim that cloud credentials, Firebase access, or a local model are currently available on a user's device.
