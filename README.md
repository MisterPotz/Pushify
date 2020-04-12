# Pushify
- Задача `Пост во все социальные сети`
- **Проектирование сервиса**
    - Использованные технологии
     
     Приложение сделано на платформу Android. Язык разработки - Kotlin. В процессе использовались
     
        - стандартные библиотеки Android
        - DI-библиотека Dagger 2 (для лучшей изолированности отдельных частей приложения)
        - Google Architecture Components (Lifecycle, Navigation, Room) - новые библиотеки от Google для повышения качества кода
        - Gson (де-/сериализация)
        - Kotlin-корутины (для асинхронной работы отдельных частей приложения)
        - VK sdk (уже есть отдельный модуль ВК на Андроид, поэтому было решено использовать его)
        - Facebook Sdk (то же самое)
        
    - Пользовательский интерфейс

      Это мобильное приложение на Android
      
    - Опишите формат ответа (текст, аудио файл, изображение), который вернется пользователю со стороны сервера и процесс его генерации.
        - **Пример:**

            Результат отправки поста формируется в виде списка, где каждым элементом выступает результат для соответствующей соц сети, где был зарегистрирован пользователь.
            То есть, после нажатия "Send" отображается такая структура:

            |Results          |
            
            |Net 1       OK   |
            
            |Net 2       Fail |

- **Продемонстрируйте работу сервиса.**
    - Запишите небольшое видео работы приложения в формате записи экрана. Желательно с комментариями в рамках видео. (чтобы не искать самостоятельно сервис для записи экрана - можно использовать [Скриншотер](https://xn--e1affnfjebo2d.xn--p1ai/?gclid=CjwKCAjwg6b0BRBMEiwANd1_SBcMLW1RXyRvgrW4rKHk1IaGoh1Kb2tnO2TIlpYpyCEyoc0TK3fpghoCuh8QAvD_BwE), [Loom](https://www.loom.com/), [ABS](https://obsproject.com/ru), [Zoom](https://zoom.us/))

- **Распишите по шагам весь процесс работы программы.**
    - **Функции программы**

        - Для пользователя

            - Возможность регистрации новых соц сетей (поддерживаемых приложением)
            - Список зарегистрированных соц сетей (все сети сохраняются на ЖД устройства - при перезапуске все останется)
            - Возможность отсылки разом одного поста на все зарегистрированные соцсети
        - Для программиста, который захотел расширить банк поддерживаемых сетей

            - Все сдк и отдельные инструменты для работы с каждой соцсетью закрыты интерфейсами и методами, представляющими собой единое АПИ для всех сетей. Использование DI позволило
            сделать код более изолированным от других частей, слабая связь способствует относительно легкому расширению поддерживаемых сетей.

        - Workflow

            - Пользователь захотел новую сеть. Нажимает на кнопку добавления
            - Открывается окно выбора новой соц сети. Пользователь выбирает желаемую и нажимает подтверждение.
            - Пользователя перенаправляют на SDK (экран) выбранной сети для авторизации.
            - Данные (токен) для этой сети сохраняются в private БД приложения (но это не обязательно, если сдк сам может сохранять эти данные авторизации)
            - Пользователь может повторить все шаги выше
            - После этого можно сделать пост. Пользователь нажимает кнопку создания поста.
            - Вводит в поле контента то, что хочет поведать остальным.
            - Подтверждает
            - Происходит проход по всем соц сетям, где активна авторизация. Дергаются методы интерфейсов всех этих элементов (но тот, кто эти элементы дергает, не знает, какая сдк на самом деле за этим стоит)
            - Приходит результат отправки постов, где каждой авторизации соответствует статус отправки


- **Как запустить вашу программу?**
    Для запуска можно скачать апк, который лежит в репозитории (апк - файл установки на Android, его можно перенести на устройство и запустить через файловый менеджер там).