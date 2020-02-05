package org.fasttrackit.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.config.ObjectMapperConfiguration;
import org.fasttrackit.domain.Task;
import org.fasttrackit.transfer.CreateTaskRequest;
import org.fasttrackit.transfer.UpdateTaskRequest;
import service.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private TaskService taskService = new TaskService();
//endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
setAccesControlHeaders(resp);


        CreateTaskRequest request = ObjectMapperConfiguration.objectMapper.readValue(req.getReader(), CreateTaskRequest.class);
        try {
            taskService.createTask(request);
        } catch (SQLException | ClassNotFoundException e) {
           resp.sendError(500, "Internal server error :"+ e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id= req.getParameter("id");
        setAccesControlHeaders(resp);

        try {
            taskService.deleteTask(Long.parseLong(id));
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(500, "Internal server error :"+ e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String id= req.getParameter("id");
        UpdateTaskRequest request = ObjectMapperConfiguration.objectMapper.readValue(req.getReader(), UpdateTaskRequest.class);
        setAccesControlHeaders(resp);


        try {
            taskService.updateTask(Long.parseLong(id), request);
        } catch (SQLException e) {
            resp.sendError(500, "Internal server error :"+ e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccesControlHeaders(resp);

        try {
            List<Task> tasks = taskService.getTasks();

           String response = ObjectMapperConfiguration.objectMapper.writeValueAsString(tasks);
           resp.getWriter().print(response);

        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(500, "Internal server error :"+ e.getMessage());
        }


    }
//CORS configuration ( Cross Origin Resourse Sharing)
    private void setAccesControlHeaders(HttpServletResponse resp){

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "content-type");

    }

    {


    }
}

