package uk.co.squarecloud.sqlidemos.simple;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import uk.co.squarecloud.sqlidemos.database.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class SimpleSqli extends AbstractHandler
{
    private Connection connection;

    public SimpleSqli(Connection connection) {
        this.connection = connection;
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String id = request.getParameter("id");
        if(id == null) {
            response.getWriter().println("ID required");
        }

        String token = request.getParameter("authtoken");
        if(token == null) {
            response.getWriter().println("Token required");
        }

        if (token != null && id != null) {
            try {
                // Now we want to execute an sql statement of the form
                // 'select name from users where id={id} and authtoken={authtoken}
                // First do this in an unsafe way and exploit it to login without knowing the authtoken
                // Then make it safe.
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.getResultSet();
                boolean hasRow = resultSet.next();

                if(hasRow) {
                    String name = resultSet.getString(1);
                    response.getWriter().println("<h1>Hello " + name + "</h1>");
                } else {
                    response.getWriter().println("<h1>Not authenticated</h1>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        baseRequest.setHandled(true);
    }

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        Database database = new Database();

        server.setHandler(new SimpleSqli(database.getConnection()));

        server.start();
        server.join();
    }
}
