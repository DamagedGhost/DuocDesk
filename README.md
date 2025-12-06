# DuocDesk 

¡Hola!  Bienvenido/a a nuestro proyecto **DuocDesk**.

Esta es la app que desarrollamos para el ramo de **Desarrollo de Aplicaciones Móviles**. La idea es que sea una app para estudiantes de Duoc, y este es el resultado de nuestras evaluaciones 2 y 3.

---

## ¿Qué tiene de bacán esta app?

Es una app inspirada en los tableros de trabajo Trello, donde los estudiante de DuocUC pueden registrarse y crear espacios de trabajo para sus equipos.
Estas son las "features" principales:

* **Autenticación de Usuarios:** Puedes crear una cuenta y luego iniciar sesión.
* **Formularios:** ¡Validamos todo! No puedes enviar formularios vacíos, los correos tienen que parecer correos y las contraseñas tienen un mínimo de caracteres. Todo con mensajes de error bonitos.
* **Persistencia Local:** ¡No se olvida de ti! Usamos **Room** (SQLite) para guardar tu cuenta directamente en el teléfono. Puedes cerrar la app y tu usuario seguirá ahí.
* **Gestión de Estado Moderna:** Usamos `StateFlow` y `ViewModel` para manejar el estado. La UI reacciona solita a los cambios (como mostrar un *loading* spinners ).
* **Recursos Nativos:** ¡Integramos la **cámara**! Puedes ir a tu perfil y ver un preview en vivo de la cámara de tu dispositivo movil.
* **Navegación Fluida:** Todo conectado con Compose Navigation, para que moverte entre pantallas sea suavecito.
---

##  ¿Quieres probarla?

¡Fácil! Solo necesitas Android Studio .

1.  Clona este repositorio:
    ```bash
    git clone https://github.com/DamagedGhost/DuocDesk
    ```
2.  Abre el proyecto con Android Studio.
3.  Espera que Gradle haga su magia y descargue todo.
4.  Dale al botón verde de **'Run' ** en un emulador o en tu teléfono.
5.  ¡Listo! Ya puedes probarla.

Nota importante: No requiere configuraciones adicionales. Retrofit ya apunta al backend en: http://98.91.150.2:4000/

---

## Tecnologías Utilizadas

### Frontend Android
* **Kotlin**
* **Jetpack Compose**
* **MVVM + StateFlow**
* **AndroidX Navigation**
* **Coil** (carga de imágenes)
* **Coroutines**

### Networking
* **Retrofit**
* **Gson Converter**
* **Multipart Upload**

### API Externa
* **GitHub REST API**: Permite listar repositorios del usuario mediante token personal.

### Testing
* **JUnit 4**
* **MockK**
* **Compose UI Testing**

---

## Funcionalidades Principales

### Autenticación
* Login
* Registro
* Sesión global en UserSession

### Gestión de Perfil
* Editar nombre, apellido, carrera y edad.
* Subida de foto a GridFS desde la cámara.
* Visualización de foto en tiempo real.
* Persistencia local para vista offline.

### Consumo API Interna (Node.js)
* `/api/usuarios`
* `/api/usuarios/login`
* `/api/usuarios/:id`
* `/api/usuarios/:id/foto`

### Consumo API Externa (GitHub API)
* Ingreso de Token.
* Listado de repositorios públicos/privados del usuario.
* Uso de headers: "Authorization: token <TOKEN>"

---

## Pruebas Unitarias Incluidas

* `LoginViewModelTest`
* `RegisterViewModelTest`
* `GitHubViewModelTest`
* Prueba UI: `LoginScreenTest`
* Pruebas base de proyecto

---

## Hecho por

Este proyecto fue creado en equipo por:

* **DamagedGhost**
* **diegoparra-git**
* **Ekkondido**

¡Gracias por revisar nuestro proyecto! ❤️
