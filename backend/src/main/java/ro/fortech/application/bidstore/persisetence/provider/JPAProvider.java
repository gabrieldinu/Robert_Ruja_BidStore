package ro.fortech.application.bidstore.persisetence.provider;

/**
 * Created by robert.ruja on 03-Apr-17.
 */

//@ManagedBean
public interface JPAProvider {

    //TODO: make hibernate session generic

    public GenericSession getSession();
}
