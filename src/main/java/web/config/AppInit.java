package web.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Метод, указывающий на класс конфигурации
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }


    // Добавление конфигурации, в которой инициализируем ViewResolver, для корректного отображения jsp.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }


    /* Данный метод указывает url, на котором будет базироваться приложение */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
//    Это метод onStartup интерфейса ServletContainerInitializer.
//    Метод onStartup вызывается при запуске контейнера сервлетов и используется для регистрации и настройки фильтров в веб-приложении.
//    В этом коде вызов super.onStartup(aServletContext) вызывает метод onStartup родительского класса. Вероятно,
//    это приведет к инициализации контейнера сервлетов с некоторыми конфигурациями по умолчанию.
//
//    После этого код создает фильтр с именем «encodingFilter», используя класс CharacterEncodingFilter,
//    и добавит его в контекст сервлета. Метод setInitParameter используется для установки
//    для параметра кодирования значения «UTF-8», а для параметра forceEncoding — значения «true».
//    Метод addMappingForUrlPatterns используется для сопоставления этого фильтра со всеми URL-адресами в веб-приложении.
//
//    Наконец, код вызывает метод registerHiddenFieldFilter и передает ему контекст сервлета.
//    Этот метод добавит еще один фильтр в контекст сервлета.

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        FilterRegistration.Dynamic encodingFilter = aServletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
        registerHiddenFieldFilter(aServletContext);
    }

    //Этот метод регистрирует фильтр в ServletContext веб-приложения.
    // Регистрируемый фильтр — это «hiddenHttpMethodFilter», и он создается с использованием класса «HiddenHttpMethodFilter».
    // Метод addFilter вызывается в ServletContext, передавая имя фильтра и новый экземпляр класса фильтра.
    //
    //Затем для возвращенного объекта FilterRegistration вызывается метод addMappingForUrlPatterns,
    // который сопоставляет фильтр со всеми URL-адресами в веб-приложении. Аргументы, передаваемые в «addMappingForUrlPatterns», следующие:
    //
    //«null» — указывает, что никакие объекты ServletRegistration не должны учитываться при отображении фильтра.
    //«true» — указывает, что этот фильтр следует применять ко всем отправкам запросов, включая асинхронные отправки.
    //«/*» — указывает, что фильтр следует применять ко всем URL-адресам в приложении.
    //Этот код регистрирует фильтр «hiddenHttpMethodFilter» для обработки методов HTTP, таких как PUT или DELETE, которые напрямую не поддерживаются HTML-формами.

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true,"/*");
   }


}