# Практическая работа №8 (Модификация работы №7, пункты 5 и 6 не реализованы)
### Вариант №15: Посуда

## Spring JMS

**Цель**: ознакомиться с механизмом JMS в Spring.

**Общая постановка задачи**: Изменить приложение из практического задания №7, №6 или №5 (на усмотрение студента) и добавить следующие возможности (пункты со "снежинкой" желательны, но не обязательны):
1) Настроить очередь (Для ActiveMQ или любого другого брокера сообщений JMS) приема сообщений для администратора.
2) При выполнении операций добавления, удаления или редактирования ресурса через REST API / форму создавать соответствующие уведомления и отправлять их в очередь.
3) Любым удобным способом (можно через терминал) продемонстрировать извлечение административных сообщений о выполненных операциях (из п.2).
4) Добавить кнопку-ссылку «купить» на форме. После этого в брокер сообщений отправляется сообщение о том, какой «товар»/сущность хочет купить пользователь.
5) *В п.4 "товар" помечается как купленный и не будет показан в общем списке товаров. Необходимо добавить соответствующий столбец, или просто удалить запись о купленном товаре из БД, но перед этим не забыть отправить информацию о товаре в брокер сообщений.
6) *Реализовать приложение-сервис приемки сообщений, которое принимает сообщение и на основе содержимого сообщения отправляет e-mail администратору по некоторому адресу (можно использовать константную строку вашего почтового ящика в домене СФУ) о том, что у него хотят купить товар.

## Инструкция по сборке и запуску
Убедитесь, что на вашем компьютере присутствует [JDK](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)

```
git clone https://github.com/waffflezz/rkis-8.git
cd rkis-8
psql -U postgres -h localhost -f create_db.sql
activemq start
sh mvnw package -Dallow.run=false 
java -jar target/rkis-8-0.0.1-SNAPSHOT.jar -Dallow.run=true
```

Открываем в браузере [страницу localhost](http://127.0.0.1:8080)

Пример работы брокера:

![image](https://github.com/waffflezz/rkis-8/assets/56751225/b4f48745-a0a3-4007-8f2e-3252e3215424)

Отправка сообщений была реализована только в web без использования REST. Функционал REST из прошлой работы был сохранён. Сообщения отправляются из контроллера и принимаются отдельным потоком, который проверяет очередь сообщений каждые 200мс.

При наличии сообщений он выводит их в терминал, что мы можем видеть на скриншоте выше.

_Для сборки необходимо иметь [Maven](https://maven.apache.org/download.cgi) на компьютере_