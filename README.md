# Graduate_work_Team-2

#  Дипломный проект
## Командная дипломная работа. Поток - DSprint 5.0.

Задача нашей команды написать бекенд-часть сайта на Java для готовой фронтенд части и реализовать следующий функционал:

Авторизация и аутентификация пользователей.\
Распределение ролей между пользователями: пользователь и администратор.\
CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления, а пользователи — только свои.\
Под каждым объявлением пользователи могут оставлять отзывы.\
В заголовке сайта можно осуществлять поиск объявлений по названию.\
Показывать и сохранять картинки объявлений.\
В качестве шаблона был предоставлен файл [Openapi](https://github.com/BizinMitya/front-react-avito/blob/v1.12/openapi.yaml)

## Техническое задание проекта:
- [Техническое Задание на проект](https://skyengpublic.notion.site/64113e0a2641475c9ad9bea93144afff)

## Комада разработчиков "Team-2":

 - [Екатерина Одокиенко](https://github.com/KatOli4ka)
 - [Екатерина Токан](https://github.com/KaterinaT666/)
 - [Татьяна Богодвид](https://github.com/TatyanaBogodvid)
 - [Егор Журавлёв](https://github.com/icedragal)
 - [Ильдар Амерханов](https://github.com/ildar1902)
 - [Евгений Давыдов](https://github.com/ITevgeniydav2022)
 
 
## Стек технологий:
**В проекте используются**:
 
* Backend:
    - Java 17
    - Maven
    - Spring Boot
    - Spring Web
    - Spring Data
    - Spring JPA
    - Spring Security
    - GIT
    - REST
    - Swagger
    - Lombok
    - Stream API
* SQL:
    - PostgreSQL
    - Liquibase
* Frontend:
    - Docker образ

## Запуск:
**Что необходимо для запуска работы сайта:**
- Клонировать проект в среду разработки (IntelliJ IDEA)
- Прописать properties в файле **[application.properties](src/main/resources/application.properties)**
- Запустить **[Docker](https://www.docker.com)**
- Запустить **[Docker образ](https://drive.google.com/file/d/1UZTpeTAQpC4ANkHEFAGK2yjTFzZhXLPz/view)**
- Запустить метод **main** в файле **[HomeworkApplication.java](src/main/java/com/example/graduate_work_team2/GraduateWorkTeam2Application.java)**

После выполнения всех действий сайт будет доступен по ссылке http://localhost:3000 и Swagger по [ссылке](https://editor.swagger.io/).

------
