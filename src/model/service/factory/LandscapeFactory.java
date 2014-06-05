package model.service.factory;

import model.service.LandscapeService;

public interface LandscapeFactory<Key> {

	LandscapeService<Key> manufacture();
}
