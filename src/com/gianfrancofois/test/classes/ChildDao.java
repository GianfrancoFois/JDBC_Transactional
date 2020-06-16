package com.gianfrancofois.test.classes;

import com.gianfrancofois.annotation.Transactional;

public class ChildDao extends Dao {

    @Transactional
    public void transactionalChildMethod() {

    }

}
