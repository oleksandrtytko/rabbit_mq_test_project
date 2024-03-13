Project implements following features:

- RabbitMQ producers using `rabbitTemplate`;
- RabbitMQ consumers using `@RabbitListener`;
- Manual message acknowledgments;
- Queue declaration (creation);
- RabbitMQ set up using `docker-compose`.

Project workflow:

1. Every second RabbitMQ message is produced and send to the `income_queue` queue.
2. RabbitMQ listeners (`income_queue` queue):
    1. Concurrently consume messages from the queue.
    2. Process data.
    3. Send message with processed data to the `processed_queue` queue.
    4. Acknowledge received message.
3. RabbitMQ listeners (`processed_queue` queue):
    1. Concurrently consume messages from the queue.
    2. Add message data to the list.
    3. Acknowledge received message.
4. Every second the list is analyzed and average value is logged in case enough values are provided.
