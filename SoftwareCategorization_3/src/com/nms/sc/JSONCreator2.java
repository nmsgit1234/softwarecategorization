package com.nms.sc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;

import au.com.bytecode.opencsv.CSVReader;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nms.sc.beans.Category;
import com.nms.sc.beans.Item;

public class JSONCreator2 {
	public static final String categoriesPath = "D:/Nasirrepository/SoftwareChoices/WebContent/Software_categories.txt";
	public static final String itemsPath = "D:/Nasirrepository/SoftwareChoices/WebContent/Software_items.json";
	public static final String csvFilePath = "D:/Nasirrepository/SoftwareChoices/WebContent/SoftwareTechnologiesGrouping.csv";
	public static HashMap<String,String> pathMap = new HashMap<String,String>();
	public static List<Category> categories = new ArrayList<Category>();
	
	public static List<Item> items = new ArrayList<Item>();

	public static void main(String args[]) {
		

		try {
			CSVReader reader = new CSVReader(new FileReader(csvFilePath));
			List myEntries = reader.readAll();
			for (int z = 1; z < myEntries.size(); z++) {

				String[] y = (String[]) myEntries.get(z);

				String cat0 = y[0];
				String cat1 = y[1];
				String cat2 = y[2];
				String cat3 = y[3];
				String cat4 = y[4];
				String catId = y[5];
				String itemName = y[6];
				String licensing = y[7];
				String url = y[8];
				String platform = y[9];
				String desc = y[10];

				// Add hyperlink to the itemName to point to the website.
				if (url != null && url.trim().length() > 0) {
					itemName = "<a href='" + url + "'target='_blank'>"
							+ itemName + "</a>";
				}

				// Check if the main category exists in the list.
				boolean isRootCatExists = false;
				Category category0 = null;
				for (Category cat : categories) {
					if (cat.getLabel().equalsIgnoreCase(cat0)) {
						isRootCatExists = true;
						category0 = cat;
						break;
					}
				}
				if (!isRootCatExists) {
					category0 = new Category(cat0);
					categories.add(category0);
				}

				// Prepare the link for the lowest category
				// String labelFormat =
				// String.format("<a href='/softwarefiles/category_items_%s.html'>%s</a>","12","Test category");
				String labelFormat = "<a href='category_items_deails.html?categoryid=%s&path=%s' target='iframe_b'>%s</a>";
				// String formattedString =
				// String.format("Order with OrdId : %d and Amount: %d is missing",
				// 40021, 3000);

				String path = "";

				// Iterate through Category1
				List<Item> catItems = null;
				if (cat1 != null && cat1.trim().length() > 0) {
					category0 = getUpdatedCategory(category0, cat1);
					Category category1 = category0.getChild(cat1);

					if (cat2 == null || cat2.trim().length() == 0) {
						path = cat0 + "%20%20%20>%20%20%20" + cat1;
						cat1 = String.format(labelFormat, catId, path, cat1);
						catItems = createItem(catId, itemName, desc, licensing,platform);
						items.addAll(catItems);
						category1.addItems(catItems);
						pathMap.put(catId, path);

					}

					if (cat2 != null && cat2.trim().length() > 0) {
						category1 = getUpdatedCategory(category1, cat2);
						Category category2 = category1.getChild(cat2);

						if (cat3 == null || cat3.trim().length() == 0) {
							path = cat0 + "%20%20%20>%20%20%20" + cat1
									+ "%20%20%20>%20%20%20" + cat2;
							cat2 = String
									.format(labelFormat, catId, path, cat2);
							catItems = createItem(catId, itemName, desc,
									licensing,platform);
							items.addAll(catItems);
							category2.addItems(catItems);
							pathMap.put(catId, path);
						}

						if (cat3 != null && cat3.trim().length() > 0) {

							category2 = getUpdatedCategory(category2, cat3);
							Category category3 = category2.getChild(cat3);

							if (cat4 == null || cat4.trim().length() == 0) {
								path = cat0 + "%20%20%20>%20%20%20" + cat1
										+ "%20%20%20>%20%20%20" + cat2
										+ "%20%20%20>%20%20%20" + cat3;
								cat3 = String.format(labelFormat, catId, path,
										cat3);
								catItems = createItem(catId, itemName, desc,
										licensing,platform);
								items.addAll(catItems);
								category3.addItems(catItems);
								pathMap.put(catId, path);
							}

							if (cat4 != null && cat4.trim().length() > 0) {

								category3 = getUpdatedCategory(category3, cat4);
								Category category4 = category3.getChild(cat4);

								path = cat0 + "%20%20%20>%20%20%20" + cat1
										+ "%20%20%20>%20%20%20" + cat2
										+ "%20%20%20>%20%20%20" + cat3 + "%20%20%20>%20%20%20" + cat4;
								cat3 = String.format(labelFormat, catId, path,
										cat3);
								catItems = createItem(catId, itemName, desc,
										licensing,platform);
								items.addAll(catItems);
								category4.addItems(catItems);
								pathMap.put(catId, path);
							}
						}
					}
				} else {
					path = cat0;
					cat0 = String.format(labelFormat, catId, path, cat0);
					catItems = createItem(catId, itemName, desc, licensing,platform);
					items.addAll(catItems);
					category0.addItems(catItems);
					pathMap.put(catId, path);
				}

			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//create the URL for the lowest category:
		for (Category category:categories){
			updateLowestCategoryLabelwithURL(category,"");
		}
		
		//Sort by category Name
		
		if (categories.size() > 0) {
		    Collections.sort(categories, new Comparator<Category>() {
		        @Override
		        public int compare(final Category object1, final Category object2) {
		            return object1.getLabel().compareTo(object2.getLabel());
		        }
		       } );
		   }
		 
		/*
		 * 
		 * 
		 * 
		 * Category root0 = new Category("Application Development");
		 * 
		 * Category root01 = new Category("Reporting"); Category root011 = new
		 * Category("Tools");
		 * 
		 * 
		 * 
		 * Category root02 = new Category("Development Utilities");
		 * 
		 * Category root03 = new Category("Networking");
		 * 
		 * Category root04 = new Category("WebServices"); Category root041 = new
		 * Category("RESTFull");
		 * 
		 * 
		 * root0.addChild(root01); root0.addChild(root02);
		 * root0.addChild(root03); root0.addChild(root04);
		 * 
		 * root01.addChild(root011);
		 * 
		 * root04.addChild(root041);
		 */
		ObjectMapper mapper = new ObjectMapper();
		// User user = new User();
		// mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		// mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
		// true);
		// mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		try {
			// System.out.println(mapper.writeValueAsString(user));
			// mapper.writeValue(System.out, user);
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.writerWithDefaultPrettyPrinter().writeValue(
					new File(categoriesPath), categories);
			mapper.writerWithDefaultPrettyPrinter().writeValue(
					new File(itemsPath), items);

			// mapper.writerWithDefaultPrettyPrinter().writeValue(System.out,
			// categories);
			// mapper.writeValue(System.out,categories);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Category getUpdatedCategory(Category parent, String child) {
		if (!parent.isChildExists(child)) {
			if (parent.getChildren() == null) {
				Set<Category> newSet = new HashSet<Category>();
				newSet.add(new Category(child));
				parent.setChildren(newSet);

			} else {
				parent.getChildren().add(new Category(child));
			}
		}
		return parent;
	}

	public static List<Item> createItem(String categoryId, String itemName,
			String desc, String licensing, String platform) {
		
		List<Item> items = new ArrayList<Item>();
		Item item = null;
		// Add related item
		StringTokenizer tokens = new StringTokenizer(categoryId,"|");
		while (tokens.hasMoreTokens()){
			categoryId = tokens.nextToken();
			if (categoryId != null && categoryId.trim().length() > 0) {
				item = new Item();
				item.setParentCategoryId(categoryId);
				if (itemName != null && itemName.trim().length() > 0) {
					item.setItemName(itemName);
					if (desc != null && desc.trim().length() > 0) {
						item.setItemDescription(desc);
					}
					if (licensing != null && licensing.trim().length() > 0) {
						item.setLicensingInfo(licensing);
					}
					if (platform != null && platform.trim().length() > 0) {
						item.setPlatform(platform);
					}
	
				}
			}
			items.add(item);
		}
		return items;
	}
	
	public static void updateLowestCategoryLabelwithURL(Category category,String catPath){
		//create the URL for the lowest category:
		String path =  catPath + "%20%20%20>%20%20%20" + category.getLabel();
		if (category.hasChildrens()){
			if (category.hasItems() && (category.getLabel().indexOf("<a") == -1)){
				Set<Item> catItems = category.getItems();
				for (Item catItem:catItems){
					String labelFormat = "<a href='category_items_deails.html?categoryid="+ catItem.getParentCategoryId() +"&path="+pathMap.get(catItem.getParentCategoryId())+"' target='iframe_b'>"+category.getLabel() +"</a>";
					category.setLabel(labelFormat);
					catPath="";
					continue;
				}
			}
			
			for(Category subCategory:category.getChildren()){
/*				if (subCategory.hasItems()){
					Set<Item> catItems = subCategory.getItems();
					if (catItems != null && catItems.size() > 0 ) {
						for (Item catItem:catItems){
							String labelFormat = "<a href='category_items_deails.html?categoryid="+ catItem.getParentCategoryId() +"&path="+pathMap.get(catItem.getParentCategoryId())+"' target='iframe_b'>"+category.getLabel() +"</a>";
							subCategory.setLabel(labelFormat);
							catPath="";
							continue;
						}
					}
				}
*/				path = path + "%20%20%20>%20%20%20" + subCategory.getLabel();
				updateLowestCategoryLabelwithURL(subCategory,path);
				//path = "";
			}
			
		} else if (category.hasItems() && (category.getLabel().indexOf("<a") == -1)){
			Set<Item> catItems = category.getItems();
			for (Item catItem:catItems){
				String labelFormat = "<a href='category_items_deails.html?categoryid="+ catItem.getParentCategoryId() +"&path="+pathMap.get(catItem.getParentCategoryId())+"' target='iframe_b'>"+category.getLabel() +"</a>";
				category.setLabel(labelFormat);
				catPath="";
				continue;
			}
		}
	}

}
