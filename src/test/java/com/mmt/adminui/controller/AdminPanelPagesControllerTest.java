package com.mmt.adminui.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AdminPanelPagesControllerTest {
	
	private AdminPanelPagesController adminPanelPagesController=new AdminPanelPagesController();

	@Test
	public void testIndex() {
		String retValue=adminPanelPagesController.index();
		assertEquals("index",retValue);
	}

}
