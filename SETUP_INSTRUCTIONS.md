# 🔥 Hot Delivery - Android App

## Struttura del Progetto
```
HotDelivery/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/hotdelivery/app/
│       │   ├── MainActivity.kt           ← Splash screen
│       │   ├── RegisterActivity.kt       ← Registrazione + scelta ruolo
│       │   ├── LoginActivity.kt          ← Login
│       │   ├── CookDashboardActivity.kt  ← Dashboard cuoco
│       │   └── ClientDashboardActivity.kt← Dashboard cliente
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml
│           │   ├── activity_register.xml
│           │   ├── activity_login.xml
│           │   ├── activity_cook_dashboard.xml
│           │   └── activity_client_dashboard.xml
│           ├── drawable/
│           │   ├── header_gradient.xml
│           │   ├── header_gradient_orange.xml
│           │   ├── splash_background.xml
│           │   ├── role_card_selector_cook.xml
│           │   └── role_card_selector_client.xml
│           ├── anim/
│           │   ├── fade_in_up.xml
│           │   └── fade_in_up_delayed.xml
│           ├── font/
│           │   └── pacifico.xml
│           └── values/
│               ├── colors.xml
│               ├── strings.xml
│               ├── themes.xml
│               └── font_certs.xml
├── build.gradle
└── settings.gradle
```

## 🚀 Setup in Android Studio

### 1. Firebase
1. Vai su https://console.firebase.google.com
2. Crea un nuovo progetto "HotDelivery"
3. Aggiungi un'app Android con package: `com.hotdelivery.app`
4. Scarica `google-services.json` e copialo in `app/`
5. Abilita **Authentication > Email/Password**
6. Abilita **Firestore Database**

### 2. Importa il Progetto
1. Apri Android Studio
2. **File > Open** → seleziona la cartella `HotDelivery`
3. Aspetta la sincronizzazione Gradle

### 3. Logo
- Sostituisci il file `app/src/main/res/mipmap-*/ic_launcher.png` con il tuo logo
- Usa **Image Asset Studio** (click destro su `res > New > Image Asset`)

### 4. Run
- Collega un dispositivo Android o usa un emulatore
- Premi ▶️ Run

---

## 🎨 Colori del Logo
| Colore | HEX |
|--------|-----|
| Rosso scuro | `#C0392B` |
| Arancione | `#E8621A` |
| Arancione chiaro | `#FF8C42` |

## 📱 Flusso App
```
Splash (2.5s)
    └── Non loggato → RegisterActivity
            ├── Scegli: 👨‍🍳 Cuoco → CookDashboard
            └── Scegli: 🍽️ Cliente → ClientDashboard
    └── Già loggato → Dashboard diretta (in base al ruolo salvato)
```

## 🔧 Prossimi Step Suggeriti
- Aggiungere foto profilo con Firebase Storage
- Cuoco: form per aggiungere piatti al menu
- Cliente: lista ristoranti/cuochi disponibili, carrello, ordini
- Notifiche push con Firebase Messaging
