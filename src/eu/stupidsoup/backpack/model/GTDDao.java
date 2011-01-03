package eu.stupidsoup.backpack.model;

public interface GTDDao {
	public GTDList getGTDList(Long id);
	public GTDList getGTDListByName(String name);
}
