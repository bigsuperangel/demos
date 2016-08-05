package util;

public interface Observer<S extends Observable<?, A>, A> {
	public void update(S o, A a);
}
