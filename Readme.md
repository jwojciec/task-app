- curl -i -X GET localhost:8888/tasks

- curl -i -X POST localhost:8888/tasks -H "Content-Type: application/json" -d '{"id":"11113001-7559-4234-b288-8e40e9694e46","name":"zmywanie","text":"pozmywac naczynia"}'

- curl -i -X GET localhost:8888/tasks/11113001-7559-4234-b288-8e40e9694e46

- curl -i -X PUT localhost:8888/tasks/11113001-7559-4234-b288-8e40e9694e46 -H "Content-Type: application/json" -d '{"id":"11113001-7559-4234-b288-8e40e9694e46","name":"pranie","text":"wyprac koszulki"}'

- curl -i -X DELETE localhost:8888/tasks/11113001-7559-4234-b288-8e40e9694e46

