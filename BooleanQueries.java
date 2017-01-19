package for_submission_project_package;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


import org.apache.lucene.index.Fields;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.document.Field;

public class trial11 {
	
	public static List<Integer> create_postings(BytesRef curr_term, IndexReader reader, String curr_field) throws IOException {
		
		List<Integer> list = new LinkedList<Integer>();

		PostingsEnum postenum = MultiFields.getTermDocsEnum(reader, curr_field, curr_term);

		while((postenum.nextDoc() != PostingsEnum.NO_MORE_DOCS))
		{
		list.add(postenum.docID());
		}
		return list;
	}
	
	public static void main(String[] args) throws Exception {
	
	//PrintStream out = new PrintStream(new FileOutputStream("/home/jayaraj/workspace/trial1/bin/trial1/text1_out.txt"));
	PrintStream out = new PrintStream(new FileOutputStream(args[1]));	
	System.setOut(out);
	
	//String indexx = "/home/jayaraj/Study/CS_IR/irproject2/index";
	String indexx = args[0];
	IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexx)));
	
	//gets all fields including 'id' and '__version__' fields.
	Fields fieldssss = MultiFields.getFields(reader);
	Iterator itr_fieldss = fieldssss.iterator();
	
	HashMap<String,List<Integer>> hmap = new HashMap<String,List<Integer>>();
	
	int total_num_of_fields=0;
	int total_terms_all_fields = 0;
	while(itr_fieldss.hasNext()){
		total_num_of_fields++;
		Object curr_field = itr_fieldss.next();
		//System.out.println("current field is : " + curr_field);
		if ((curr_field.toString().equals("id") == false) && (curr_field.toString().equals("_version_") == false) ){
			Terms terms = MultiFields.getTerms(reader,curr_field.toString());
			//TermsEnum termss = reader.terms();
			TermsEnum termsIterator = terms.iterator();
			BytesRef term;
			int total_terms_per_field = 0;
			BytesRef curr_term = termsIterator.next();
			while(curr_term != null) {
			    total_terms_per_field++;
			    		    
			    if (curr_term != null ){//&& curr_term.utf8ToString().equals("afskrivning")){
//			    	System.out.println("under : "+curr_term.utf8ToString());
			    	List<Integer> list = new LinkedList<Integer>();
			    	//System.out.println("calling");
			    	
			        list = create_postings(curr_term,reader,curr_field.toString());

			        //System.out.println("\nreturned");
			        
			        //System.out.println("ceating and displaying hashmap");
			        
			        
			        hmap.put(curr_term.utf8ToString(), list);
			        
			        List list_hashmap=  hmap.get(curr_term.utf8ToString());
//			        if (list_hashmap != null){
//			        for(int i = 0; i < list_hashmap.size(); i++) {
//			    		
//			            System.out.print(list_hashmap.get(i));
//			            System.out.print(" ");
//			        }
//			        }
			    }
			    curr_term = termsIterator.next();
			}
			
		
			//System.out.println("For field : " + curr_field.toString() + " ---> num of terms : "+ total_terms_per_field +".");
			total_terms_all_fields+=total_terms_per_field;
		}
		else{
			//System.out.println("skipped : "+curr_field);
		}
	}
	
	FileReader fileReader = new FileReader(args[2]);
	String line = null;
	BufferedReader bufferedReader = new BufferedReader(fileReader);
	
	
	
	
    String[] splitArray = null;
    
    
    
    while((line = bufferedReader.readLine()) != null) {
    	splitArray = line.split("\\s+");
    	
    	//System.out.println(Arrays.toString(splitArray)); 
    	
    	//enable this later on****************
    	//System.setOut(out);
    	
    	//need separate list_of_lists for each line
    	List<List> list_of_lists = new ArrayList<>();
    	
    	
    	for (int i = 0; i < splitArray.length; i++) {
	    	System.out.println("GetPostings");
	    	System.out.println(splitArray[i]);
	
	    	System.out.print("Postings list: ");
	    	List list_postings_ofterms=  hmap.get(splitArray[i]);
	 
    		list_of_lists.add(list_postings_ofterms);
	    	
	    	if (list_postings_ofterms !=null){
	    	for(int ii = 0; ii < list_postings_ofterms.size(); ii++) {
	    		
	            System.out.print(list_postings_ofterms.get(ii));
	            System.out.print(" ");
	        }
	    	}
	    	else{
	    		System.out.print("empty");
	    	}
	    	System.out.print("\n");
	    }
    	//need to call daat and here    
    	taatand(list_of_lists, splitArray);
    	taator(list_of_lists, splitArray);
    	daatand(list_of_lists,splitArray);
    	daator(list_of_lists, splitArray);
    	
    }
    bufferedReader.close();  		
	
    //System.out.println(Arrays.toString(list_of_lists.toArray()));
	
//	System.out.println("total number of fields :"+total_num_of_fields);
//	System.out.println("total terms alltogether : "+total_terms_all_fields);
//	System.out.println("Size of the map: "+ hmap.size());
	
	
	
	}	
	
	public static void daatand(List<List> list_of_lists,  String[] splitArray) throws InterruptedException{
		//System.out.println("list_of_listss : " + list_of_lists);
		
		//System.out.println(list_of_lists.size());
		
		int comparisons_count = 0;
		System.out.println("DaatAnd");
		for (int i=0; i<splitArray.length;i++){
			System.out.print(splitArray[i]);
			System.out.print(" ");
		}
		System.out.print("\n");
		
		int[] stores_curr_ele =  new int [list_of_lists.size()];
		Iterator[] stores_all_iters = new  Iterator[list_of_lists.size()];
		
		List<Integer> temp = Arrays.asList();
		
		
		for (int i=0; i <= list_of_lists.size()-1;i++){
			//System.out.println(i);
			if (list_of_lists.get(i) != null ){
			stores_all_iters[i]= list_of_lists.get(i).iterator();
			}
		}
		
		//System.out.println(stores_all_iters.length);
		int num_of_nulls=0;
		for (int i=0; i<= (stores_all_iters).length-1; i++){
			//System.out.println(stores_all_iters[i]);
			if(stores_all_iters[i] != null){
				stores_curr_ele[i]= (int) stores_all_iters[i].next();
			}
			else{
				stores_curr_ele[i]= -1;
				num_of_nulls++;
			}
		}
			
		
		int max =0;
		//int[] indexx =new int[3];
		
		
		
		//int j = 0 ;//for indexing indexx
		
		List<Integer> intersection_result = new LinkedList();
		//System.out.println(stores_curr_ele.length);
		
		int stuck_in_loop_count=0;
		outerloop:
		while(true){
			stuck_in_loop_count++;
			if(stuck_in_loop_count>70000){
				break outerloop;
			}
			int numb_list_present = 0;
			//System.out.print("printing curr elements list");
			//System.out.println(Arrays.toString(stores_curr_ele));
			List<Integer> indexx = new ArrayList<Integer>();
			//break outerloop;
//			Thread.sleep(4000);
			
			//comparison count is incremented by total number of lists minus 1 because max is 
			//acquired from a list and the rest of the lists are compared from it
			comparisons_count+=stores_all_iters.length-1;
			int checking_all_lists_null=0;
			for (int i=0; i<= stores_curr_ele.length-1; i++){
				//System.out.println("entering this");
				//System.out.printf("max: %s curr ele: %s\n",max, stores_curr_ele[i]);
				if (stores_curr_ele[i]>max){
					max = stores_curr_ele[i];
					//System.out.println(stores_curr_ele[i]);
					
					//System.out.printf("max: %s\nindexx: %s\n",max, indexx);
					numb_list_present=0 ;
				}
				if(stores_curr_ele[i]==-1){
					checking_all_lists_null++;
				}
				
			}
			
			if (checking_all_lists_null==stores_all_iters.length){
				break outerloop;
			}
			
			
			
			for (int i=0; i<= stores_curr_ele.length-1; i++){
				if (stores_curr_ele[i]==max){
					//System.out.printf("curr ele: %smax: %s\n",stores_curr_ele[i], max);
					numb_list_present+=1;
					indexx.add(i);
				}
			}
			
			if (numb_list_present== list_of_lists.size()){
				//System.out.printf("numb list present : %slist_of_lists size: %s\n",numb_list_present, list_of_lists.size());
				if(max != 0){
					intersection_result.add(max);
				}
			}
			
			
			//int to_break = false;
			//System.out.print("printing indexx:");
			//System.out.println(Arrays.toString(indexx.toArray()));
			
			
			
			int to_break_count = 0; 
			int all_invalid_count = 0;
			
			for (int i=0; i<= (stores_all_iters).length-1; i++){
//				System.out.println("stuckin for");
				//Thread.sleep(4000);
				
				//if all lists have already reached end then break.
				if (stores_all_iters[i]!=null){
					if ( !stores_all_iters[i].hasNext()){
						//System.out.printf("inrementing to_break_count becoz of %s\n",i);
						to_break_count++;
						if(to_break_count==splitArray.length-num_of_nulls){
							//System.out.println("breaking becox=z of this");
							break outerloop;
						}
					}
				}	
							
				//if the curr ele of a list is not the max then increment that list
				if (!indexx.contains(i) && numb_list_present != list_of_lists.size()){
					
					//one of the lists have reached the end and that list is not in max .. so end.
					if (stores_all_iters[i] != null){
						if(!stores_all_iters[i].hasNext()){
//							int temp_comparison_count = comparisons_count-(stores_all_iters.length-i);
//							comparisons_count=temp_comparison_count;
							break outerloop;
						}
					}
					
					//if not end and not max .. then increment. 
					if (stores_all_iters[i] != null){
						if(stores_all_iters[i].hasNext()){
							stores_curr_ele[i]=(int) stores_all_iters[i].next();
						}
					}
					
					if(stores_all_iters[i] == null){			
						continue;
					}
				}
				//System.out.printf("num of nulls %s",num_of_nulls);
				//System.out.printf("num list present%s\n",numb_list_present);
				if (numb_list_present == list_of_lists.size()-num_of_nulls ){
					//int temp2 = (int) stores_all_iters[i].next();
//					if (!stores_all_iters[i].hasNext()){
//						//System.out.println(stores_all_iters[i].hasNext());
//						break outerloop;
//					}
					if (stores_all_iters[i] != null){
						if(stores_all_iters[i].hasNext()){
							stores_curr_ele[i]=(int) stores_all_iters[i].next();
							}
					}
					if (stores_all_iters[i]==null){
						all_invalid_count++;
						if (all_invalid_count == stores_all_iters.length){
							break outerloop;
						}
					}
				}
				
			}
			max=0;
		}
		
		Set<String> hs = new HashSet<>();
		Set<Integer> set = new HashSet<Integer>(intersection_result);
		
		List sortedList = new ArrayList<Integer>(set);
		Collections.sort(sortedList);
		
//		System.out.println(intermediate3);	
//		System.out.println(set);
		
		System.out.print("Results: ");
		if(sortedList != null && sortedList.size() < 1){
			System.out.print("empty");
		}else{
			for (Object temp2 : sortedList){
	        	System.out.printf("%s ",temp2);
	        }
		}
		
		
		System.out.printf("\nNumber of documents in results: %s\n",sortedList.size());
		
		System.out.printf("Number of comparisons: %s\n",comparisons_count);
	
	}
		
	public static void daator(List<List> list_of_lists,  String[] splitArray) throws InterruptedException{
		
		int comparisons_count = 0;
		System.out.println("DaatOr");
		for (int i=0; i<splitArray.length;i++){
			System.out.print(splitArray[i]);
			System.out.print(" ");
		}
		System.out.print("\n");
		
		int[] stores_curr_ele =  new int [list_of_lists.size()];
		Iterator[] stores_all_iters = new  Iterator[list_of_lists.size()];
		
		List<Integer> temp = Arrays.asList();
		
		
		for (int i=0; i <= list_of_lists.size()-1;i++){
			//System.out.println(i);
			if (list_of_lists.get(i) != null ){
			stores_all_iters[i]= list_of_lists.get(i).iterator();
			}
		}
		
		int num_of_nulls=0;
		for (int i=0; i<= (stores_all_iters).length-1; i++){
			//System.out.println(stores_all_iters[i]);
			if(stores_all_iters[i] != null){
				stores_curr_ele[i]= (int) stores_all_iters[i].next();
			}
			else{
				stores_curr_ele[i]= -1;
				num_of_nulls++;
			}
		}
		//System.out.printf("number of nulls:%s\n",num_of_nulls);
		int max=0;
		int min=99999999;
		int j=0;
		int num_lists_ended=0;
		ArrayList<Integer> unionlist = new ArrayList<Integer>();  
		int stuck_in_loop_count =0;
		outerloop:
		while(true){
			stuck_in_loop_count++;
			if(stuck_in_loop_count>70000){
				break outerloop;
			}
			int num_lists_with_max = 0 ;
//			System.out.print("printing curr elements list");
//			Thread.sleep(100);
//			System.out.println(Arrays.toString(stores_curr_ele));
			for (int i=0; i<stores_curr_ele.length;i++){
				if (stores_curr_ele[i]>max){
					max = stores_curr_ele[i];				
				}
				if (!unionlist.contains(stores_curr_ele[i]) && stores_curr_ele[i]!= -1){
					unionlist.add(stores_curr_ele[i]);
				}
				if(stores_all_iters[i]!=null){
					if ( stores_all_iters[i].hasNext()){
						if (stores_curr_ele[i]<min){
							min = stores_curr_ele[i];				
						}
					}
				}
				
			}
			comparisons_count+=stores_all_iters.length-1;
//			System.out.println(max);
//			System.out.println(min);
			int list_with_not_max_but_ended = 0;
			Boolean multiple_lists_got_max = false;
			for (int i=0; i<stores_all_iters.length;i++){
				if(stores_all_iters[i]!=null){
					
					if(!stores_all_iters[i].hasNext()){
						list_with_not_max_but_ended++;
					}
					
					if(stores_curr_ele[i]<max){
						if(stores_all_iters[i].hasNext()){
							stores_curr_ele[i]=(int) stores_all_iters[i].next();
						}
					}
					
					else if(stores_curr_ele[i]==max){
						num_lists_with_max++;
						
						if(stores_all_iters[i].hasNext() && list_with_not_max_but_ended==(stores_all_iters.length-num_of_nulls-num_lists_with_max)){
							stores_curr_ele[i]=(int) stores_all_iters[i].next();
							multiple_lists_got_max = true;
						}
//						if (stores_all_iters.length - list_with_not_max_but_ended == 1){
//							if(stores_all_iters[i].hasNext()){
//								stores_curr_ele[i]=(int) stores_all_iters[i].next();
//							}
//							else{
//								break outerloop;
//							}
//						}
//												
					}
					//no need to check for curr ele > max .. no such ele.
					//an element is not max... but its list has reached end. no need to break. if such lists equal -
					//number of non null lists then break
//					if(!stores_all_iters[i].hasNext()){
//						list_with_not_max_but_ended++;
//					}
				}
				
			}	
//			if (stores_all_iters.length - list_with_not_max_but_ended == 1){
//				for (int k=0; k<stores_all_iters.length;k++){
//					if(stores_all_iters[k]!=null){
//						if(stores_curr_ele[k]==max){
//							if(!stores_all_iters[k].hasNext()){
//								stores_curr_ele[k]=(int) stores_all_iters[k].next();
//							}
//							else{
//								break outerloop;
//							}
//						}
//					}
//				}
//			}
			
			if (min == max){
				for (int k=0; k<stores_all_iters.length;k++){
					if(stores_all_iters[k]!=null){
						if(stores_curr_ele[k]==max){
							if(stores_all_iters[k].hasNext()){
								stores_curr_ele[k]=(int) stores_all_iters[k].next();
							}
							else{
//								System.out.println("min== max");
								break outerloop;
							}
						}
					}
				}
			}
			
			//******need to look into it. 
//			if(multiple_lists_got_max == true){
				for (int k=0; k<stores_all_iters.length;k++){
					if(stores_all_iters[k]!=null){
						if(stores_curr_ele[k]==max){
							if(stores_all_iters[k].hasNext() && list_with_not_max_but_ended==(stores_all_iters.length-num_of_nulls-num_lists_with_max)){
							
									stores_curr_ele[k]=(int) stores_all_iters[k].next();
								
							}
						}
					}
				}
//			}
			
			//all non null lists have max.
			if(num_lists_with_max==stores_all_iters.length-num_of_nulls){
				for (int i=0; i<stores_all_iters.length;i++){
					if(stores_all_iters[i]!=null){
						if(stores_all_iters[i].hasNext()){
							stores_curr_ele[i]=(int) stores_all_iters[i].next();
						}
						else{
							num_lists_ended++;
						}
					}
				}			
			}
			
			if (list_with_not_max_but_ended==stores_all_iters.length-num_of_nulls){
//				System.out.println("breaking becoz of list_with_not_max_but_ended");
				break outerloop;
			}
			
			if(num_lists_ended==stores_all_iters.length-num_of_nulls){
//				System.out.println("breaking becoz of num_lists_ended");
				break outerloop;
			}
		}
		
		Set<String> hs = new HashSet<>();
		Set<Integer> set = new HashSet<Integer>(unionlist);
		
		List sortedList = new ArrayList<Integer>(set);
		Collections.sort(sortedList);
		
//		System.out.println(intermediate3);	
//		System.out.println(set);
		
		System.out.print("Results: ");
		if(sortedList != null && sortedList.size() < 1) {
			System.out.print("empty");
		} else {
			for (Object temp2 : sortedList){
	        	System.out.printf("%s ",temp2);
	        }
		}
		
		
		//System.out.println("\n");
		
		System.out.printf("\nNumber of documents in results: %s\n",sortedList.size());
		
		System.out.printf("Number of comparisons: %s\n",comparisons_count);
	
		
	}
	
	public static void taator(List<List> list_of_lists,  String[] splitArray) throws InterruptedException{
		
		int comparisons_count = 0;
		System.out.println("TaatOr");
		for (int i=0; i<splitArray.length;i++){
			System.out.print(splitArray[i]);
			System.out.print(" ");
		}
		System.out.print("\n");
		
		List<Integer> intermediate = new ArrayList<>();
		
		if (list_of_lists!=null){
			for (int i=0; i<list_of_lists.size();i++){
				if(list_of_lists.get(i)!=null){
					for (int j=0;j<list_of_lists.get(i).size();j++){
						comparisons_count++;
						//System.out.println(list_of_lists.get(i).get(j));
						if( !intermediate.contains(list_of_lists.get(i).get(j))){
							intermediate.add((Integer) list_of_lists.get(i).get(j));
						}
					}
				}
			}
		}
		
		//System.out.print(intermediate);	
		
		Set<String> hs = new HashSet<>();
		Set<Integer> set = new HashSet<Integer>(intermediate);
		
		List sortedList = new ArrayList<Integer>(set);
		Collections.sort(sortedList);
		
//		System.out.println(intermediate3);	
//		System.out.println(set);
		
		System.out.print("Results: ");
		if(sortedList != null && sortedList.size() < 1){
			System.out.print("empty");
		}
		else{
			for (Object temp : sortedList){
	        	System.out.printf("%s ",temp);
	        }
		}
		
		
		System.out.print("\n");
		
		
		System.out.printf("Number of documents in results: %s\n",sortedList.size());
		
		System.out.printf("Number of comparisons: %s\n",comparisons_count);
		
	}
	
	public static void taatand(List<List> list_of_lists,  String[] splitArray) throws InterruptedException{
		
			int comparisons_count = 0;
			System.out.println("TaatAnd");
			for (int i=0; i<splitArray.length;i++){
				System.out.print(splitArray[i]);
				System.out.print(" ");
			}
			System.out.print("\n");
			
			List<Integer> intermediate = new ArrayList<>();
			//List<Integer> intermediate2 = new ArrayList<>();
			Boolean First = true;
			
			if(list_of_lists!=null){
				intermediate = list_of_lists.get(0);
			}
			else{
				intermediate = Collections.emptyList();
			}
//			System.out.println(intermediate);
			List<Integer> intermediate3 = new ArrayList<>();
			intermediate3= Collections.emptyList();
			if (list_of_lists != null){
				for (int i=1; i<list_of_lists.size();i++){
					List<Integer> intermediate2 = new ArrayList<>();
					
					if(list_of_lists.get(i)!=null){
						//System.out.println(list_of_lists.get(i));
						for (int j=0;j<list_of_lists.get(i).size();j++){		
							comparisons_count++;
							if (intermediate!=null){
								if (intermediate.contains(list_of_lists.get(i).get(j))){
									intermediate2.add((Integer) list_of_lists.get(i).get(j));
								}
							}
						}
					}
					intermediate=intermediate2;
					intermediate3=intermediate2;
				}
			}
			
			
			Set<Integer> set = new HashSet<Integer>(intermediate3);
			
			List sortedList = new ArrayList<Integer>(set);
			Collections.sort(sortedList);
			
//			System.out.println(intermediate3);	
//			System.out.println(set);
			
			System.out.print("Results: ");
			if(sortedList != null && sortedList.size() < 1){
				System.out.print("empty");
			}
			else{
				for (Object temp : sortedList){
		        	System.out.printf("%s ",temp);
		        }
			}
			
			System.out.print("\n");
			
			System.out.printf("Number of documents in results: %s\n",sortedList.size());
			
			System.out.printf("Number of comparisons: %s\n",comparisons_count);
		
	}
	
}
		
