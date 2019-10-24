package testarea;

import java.util.List;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;

public class TestArea {

	public void test() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(mainMenuOptions);

			for (int i = 0; i < mainMenuOptions.length; i++) {
				// main menu
				if (choice.contentEquals(mainMenuOptions[i])) {
					String name = mainMenuOptions[i];
					Park parkInfo = parkDAO.displayParkInfo(name);
					System.out.print(parkInfo.getName() + "\nLocation: " + parkInfo.getLocation() + "\nArea: "
							+ parkInfo.getArea() + "\nEstablished: " + parkInfo.getEstablishDate() + "\nVisitors: "
							+ parkInfo.getVisitors() + "\nDescription: " + parkInfo.getDescription() + "\n");

					// view campgrounds menu
					String menuTwoChoice = (String) menu.getChoiceFromOptions(MENU2_OPTIONS);

					if (menuTwoChoice.equals(MENU2_OPTION_VIEW_CAMPGROUNDS)) {
						List<Campground> campInfoList = campgroundDAO.getAllCampgrounds();
						System.out.println(parkInfo.getName() + " National Park Campgrounds");
						String format = "|%1$-32s|%2$-5s|%3$-5s|%4$-10s|\n";
						System.out.format(format, "Name", "Open", "Close", "Daily Fee");
						for (int j = 0; j < campInfoList.size(); j++) {
							System.out.format(format, campInfoList.get(j).getName(), campInfoList.get(j).getOpen_from(),
									campInfoList.get(j).getOpen_to(), campInfoList.get(j).getDaily_fee(), "\n");
						}
						// search
						String menuThreeChoice = (String) menu.getChoiceFromOptions(MENU3_OPTION_SEARCH_RES);
						if (menuThreeChoice.equals(MENU3_OPTION_SEARCH_FOR_RESERVATION)) {

						}
						// return
						else if (menuThreeChoice.equals(MENU3_OPTION_RETURN_TO_MENU2)) {
							System.out.println(menu.getChoiceFromOptions(MENU2_OPTIONS));

						}
						break;

					} else if (menuTwoChoice.equals(MENU2_OPTION_SEARCH_RES)) {

					} else if (menuTwoChoice.equals(MENU2_OPTION_RETURN)) {

					}

				}

				if (choice.equals(mainMenuOptions[mainMenuOptions.length - 1])) {
					System.out.println("Thank you, goodbye");
					System.exit(0);
				}
			}
		}
	}
}
