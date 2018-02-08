package com.pets_space.storages;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PoolTest extends DbInit{

    @Test
    public void getDataSource() {
        assertNotNull(Pool.getDataSource());
    }
}