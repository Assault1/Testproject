package TestAutomation.TestProject;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class AppTest1 extends BaseTest {

	@Test(priority = 1)
	public void TC1() {

		loadApplication("Mozilla", "urlKey");
		Assert.assertEquals(getTitle(), "Computers database");
		Assert.assertEquals(verifyheader("Header"), "Play sample application — Computer database");

	}

	@Test(priority = 2)
	public void TC2() {

		Assert.assertEquals(verifyCountheader("Count1", "pagination1"), true);

	}

	@Test(priority = 3)
	public void TC3() {

		Assert.assertEquals(validateTableHeading("tableheadingcompany", "tableheadingdiscontinue",
				"tableheadingintroduce", "tableheadingcomputer"), true);

	}

	@Test(priority = 4)
	public void TC4() {

		Assert.assertEquals(validatepagination("firstpage", "pagination1"), true);

	}

	@Test(priority = 5)
	public void TC5() {

		nextbutton(1, "nextbuttonlocator");
		Assert.assertEquals(validatepagination("secondpage", "pagination1"), true);

	}

	@Test(priority = 6)
	public void TC6() {

		int totalcount = totalrecord("Count1");
		nextbutton((totalcount - 1) / 10, "nextbuttonlocator");
		Assert.assertEquals(validatepagination("Count1", "pagination1"), true);

	}

	@Test(priority = 7)
	public void TC7() {

		searchComputer("Searchtext", "searchbox", "searchsubmit");
		int k = totalrecord("Count1");
		Assert.assertEquals(filterrecord("tablesplit1", "tablesplit2", k), true);

	}

	@Test(priority = 8)
	public void TC8() {

		searchComputer("Invalidtext", "searchbox", "searchsubmit");
		Assert.assertEquals(invalidtext("nothing"), true);

	}

	@Test(priority = 9)
	public void TC9() {

		clearfilter("searchbox", "searchsubmit");
		Assert.assertEquals(validatepagination("firstpage", "pagination1"), true);

	}

	@Test(priority = 10)
	public void TC10() {

		Assert.assertEquals(newcomputerpage("Add", "Computername", "Introduced", "Discontinued", "Company"), true);

	}

	@Test(priority = 11)
	public void TC11() {

		Assert.assertEquals(addnewcomputer("computername1", "introdate", "disdate", "companyname"), true);

	}

	@Test(priority = 12)
	public void TC12() {

		Assert.assertEquals(cancelbutton("Add", "Cancel"), true);

	}

	@Test(priority = 13)
	public void TC13() {

		Assert.assertEquals(mandatoryfields("computername2"), true);

	}

	@Test(priority = 14)
	public void TC14() {

		Assert.assertEquals(blank("Add", "Create"), true);

	}

	@AfterTest
	public void close() {

		driver.close();
	}
}
