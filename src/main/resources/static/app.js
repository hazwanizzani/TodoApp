document.addEventListener('DOMContentLoaded', () => {
    const todoForm = document.getElementById('todo-form');
    const todoList = document.getElementById('todo-list');

    // Fetch and display todos
    const fetchTodos = async () => {
        const response = await fetch('/todo');
        const todos = await response.json();
        displayTodos(todos);
    };

    // Display todos
    const displayTodos = (todos) => {
        todoList.innerHTML = '';
        todos.forEach(todo => {
            const todoItem = document.createElement('li');
            todoItem.className = 'todo-item';
            if (todo.completed) {
                todoItem.classList.add('completed');
            }

            todoItem.innerHTML = `
                <h3>${todo.title}</h3>
                <p>${todo.description}</p>
                <p>Due Date: ${todo.dueDate}</p>
                <p>Completed: ${todo.completed ? 'Yes' : 'No'}</p>
                <button onclick="editTodo(${todo.id})">Edit</button>
                <button onclick="markAsCompleted(${todo.id})">Mark as Completed</button>
                <button onclick="deleteTodo(${todo.id})">Delete</button>
            `;

            todoList.appendChild(todoItem);
        });
    };

    // Handle form submission
    todoForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        const dueDate = document.getElementById('dueDate').value;

        const todo = {
            title,
            description,
            dueDate
        };

        const response = await fetch('/todo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(todo)
        });

        if (response.ok) {
            fetchTodos();
            todoForm.reset();
        } else {
            alert('Error creating todo');
        }
    });

    // Edit todo
    window.editTodo = async (id) => {
        const response = await fetch(`/todos/${id}`);
        const todo = await response.json();
        document.getElementById('title').value = todo.title;
        document.getElementById('description').value = todo.description;
        document.getElementById('dueDate').value = todo.dueDate;

        todoForm.onsubmit = async (event) => {
            event.preventDefault();

            const updatedTodo = {
                title: document.getElementById('title').value,
                description: document.getElementById('description').value,
                dueDate: document.getElementById('dueDate').value
            };

            const response = await fetch(`/todo/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updatedTodo)
            });

            if (response.ok) {
                fetchTodos();
                todoForm.reset();
                todoForm.onsubmit = null;
            } else {
                alert('Error updating todo');
            }
        };
    };

    // Mark todo as completed
    window.markAsCompleted = async (id) => {
        const response = await fetch(`/todo/${id}/complete`, {
            method: 'PATCH'
        });

        if (response.ok) {
            fetchTodos();
        } else {
            alert('Error marking todo as completed');
        }
    };

    // Delete todo
    window.deleteTodo = async (id) => {
        const response = await fetch(`/todo/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            fetchTodos();
        } else {
            alert('Error deleting todo');
        }
    };

    // Initial fetch of todos
    fetchTodos();
});
