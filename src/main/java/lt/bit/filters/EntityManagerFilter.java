package lt.bit.filters;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter(filterName = "EntityManagerFilter", urlPatterns = {"/*"})
public class EntityManagerFilter implements Filter {

    private EntityManagerFactory emf;

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        emf = Persistence.createEntityManagerFactory("NewPersistenceUnit");
    }

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        EntityManager em = emf.createEntityManager();
        request.setAttribute("em", em);
        try {
            chain.doFilter(request, response);
        } finally {
            request.removeAttribute("em");
            em.close();
        }
    }

    @Override

    public void destroy() {
        emf.close();
    }
}
