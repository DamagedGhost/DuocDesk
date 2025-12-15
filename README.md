# ✨ DuocDesk – App Móvil ✨

Bienvenido/a 👋 a **DuocDesk**, una aplicación móvil desarrollada como proyecto académico para el ramo de **Desarrollo de Aplicaciones Móviles** en **Duoc UC**.

DuocDesk es una app de **gestión de proyectos colaborativos**, inspirada en Trello, pensada para que estudiantes puedan organizar trabajos, coordinar equipos y centralizar tareas desde su dispositivo móvil.

Este repositorio corresponde a las **evaluaciones 2 y 3**, integrando arquitectura moderna, buenas prácticas y comunicación con un backend propio.

---

## 🚀 ¿Qué ofrece DuocDesk?

La aplicación permite a estudiantes de Duoc UC registrarse, autenticarse y trabajar colaborativamente en tableros de tareas.

### ✨ Funcionalidades principales

- 🔐 **Autenticación de usuarios**  
  Registro e inicio de sesión con validaciones completas.

- 🧾 **Formularios validados**  
  No se permiten campos vacíos, correos inválidos ni contraseñas débiles. Todos los errores se muestran con feedback claro al usuario.

- 💾 **Persistencia local**  
  Uso de **Room (SQLite)** para mantener la sesión del usuario activa incluso al cerrar la app.

- 🔄 **Gestión de estado moderna**  
  Implementación de **ViewModel + StateFlow**, permitiendo una UI reactiva (loading states, manejo de errores, etc.).

- 📸 **Uso de recursos nativos**  
  Integración con la **cámara del dispositivo**, permitiendo visualizar un preview en tiempo real desde el perfil del usuario.

- 🧭 **Navegación fluida**  
  Navegación implementada con **Jetpack Compose Navigation** para una experiencia continua y ordenada.

- 🌐 **Comunicación con Backend**  
  Consumo de una **API REST propia (DuocDesk API)** desarrollada en Node.js, Express y MongoDB.

---

## 🧱 Arquitectura y Tecnologías

La app sigue una arquitectura moderna recomendada por Android:

- **Arquitectura:** MVVM (Model – View – ViewModel)
- **UI:** Jetpack Compose
- **Estado:** ViewModel + StateFlow
- **Persistencia:** Room (SQLite)
- **Lenguaje:** Kotlin
- **IDE:** Android Studio

---

## ▶️ Cómo descargar y ejecutar la app

> ⚠️ **Importante:** Este proyecto no requiere configuración de backend para compilar, pero sí puede requerir ajustes de Gradle dependiendo del equipo.

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/DamagedGhost/DuocDesk
```

### 2️⃣ Abrir el proyecto en Android Studio

- Abre **Android Studio**
- Selecciona **Open**
- Elige la carpeta del proyecto recién clonada

### 3️⃣ Sincronizar Gradle

Android Studio ejecutará automáticamente la sincronización de **Gradle**.

Si aparece un error relacionado con rutas locales (muy común), revisa:

- El archivo:
  ```
  gradle.properties
  ```
- O la configuración del SDK en:
  ```
  File > Settings > Android SDK
  ```

Asegúrate de que las rutas apunten correctamente al **SDK de Android instalado en tu PC**.

> 💡 Tip: muchos errores se solucionan usando la opción **“Sync Project with Gradle Files”**.

### 4️⃣ Ejecutar la aplicación

- Conecta un dispositivo físico **o** inicia un emulador
- Presiona el botón verde **Run ▶️**

La app se compilará y se ejecutará automáticamente.

---

### 🧱 APK de DuocDesk

- La APK de DuocDesk se encuentra en:

- APK > release > **DuocDesk.apk**


## ✨ Últimas Actualizaciones (v1.0.0)

### 🗂 Gestión de Proyectos (Estilo Trello)

- **CRUD de Tableros:** Creación, edición y eliminación de espacios de trabajo.
- **Listas y Tarjetas:** Estructura anidada para la gestión de tareas *(Tablero → Listas → Tarjetas)*.
- **Colaboración:** Sistema de invitaciones por correo electrónico a otros usuarios.

### 🔔 Notificaciones y Tiempo Real

- **Notificaciones In-App:** Sistema basado en *polling* para recibir alertas de invitación mientras la app está activa.
- **Alertas de Sistema:** Integración con `NotificationManager` de Android para avisos en la barra de estado.

### 📸 Multimedia y Hardware

- **Cámara Nativa:** Integración para captura y subida de fotos de perfil directamente a MongoDB mediante **GridFS**.
- **Persistencia Multimedia:** Almacenamiento optimizado de imágenes y carga eficiente mediante *Lazy Loading*.

### 🎨 Experiencia de Usuario (UX)

- **Pull-to-Refresh:** Actualización de tableros deslizando la pantalla (estilo Instagram).
- **Buscador y Favoritos:** Filtrado rápido de tableros y acceso directo a proyectos destacados.
- **Roles de Usuario:** Script de administración (`create_admin.js`) y vistas diferenciadas para el rol **ADMIN**.

### 🔌 Integraciones Externas

- **GitHub API:** Módulo para conectar y listar repositorios públicos y privados mediante **Personal Access Token**.

---

## 🧑‍💻 Equipo de Desarrollo

Proyecto desarrollado en equipo por estudiantes de Duoc UC:

- **DamagedGhost (Felipe Vásquez)**
- **diegoparra-git (Diego Parra)**
- **Ekkondido (Marcelo Mancilla)**

---

Gracias por revisar nuestro proyecto 💙  
© 2025 – **DuocDesk App Móvil**

