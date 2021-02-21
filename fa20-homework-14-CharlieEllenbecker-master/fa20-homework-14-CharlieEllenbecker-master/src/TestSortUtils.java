

import java.util.Comparator;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.util.SortUtils;

public class TestSortUtils extends LockedTestCase {
	private SortUtils<String> sort;
	private Comparator<String> forward, backward;
	private String[] ar1, ar2, ar3;
	
	@Override
	public void setUp() {
		forward = TestUtil.<String>defaultComparator();
		backward = TestUtil.reverse(forward);
		sort = new SortUtils<String>(forward);
		ar1 = new String[5];
		ar2 = new String[5];
		ar3 = new String[5];
	}
	
	public void testMerge0() {
		sort = new SortUtils<String>(forward);
		ar1[4] = "hello";
		ar2[4] = "world";
		sort.merge(5,5,5,ar1,ar2);
		assertEquals("hello",ar1[4]);
		assertEquals("world",ar2[4]);
	}
	
	public void testMerge1() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "hello";
		ar1[1] = "1";
		ar2[0] = "bye";
		ar2[1] = "2";
		sort.merge(0,0,1,ar1,ar2);
		assertEquals(Ts(431554184),ar1[0]);
		assertEquals(Ts(1831255286),ar1[1]);
		
		assertEquals(Ts(1366850943),ar2[0]);
		assertEquals(Ts(1017834401),ar2[1]);
	}
	
	public void testMerge2() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "hello";
		ar1[1] = "bye";
		ar1[2] = "1";
		ar2[0] = "2";
		ar2[1] = "3";
		ar2[2] = "4";
		sort.merge(0,1,2,ar1,ar2);
		assertEquals("hello",ar1[0]);
		assertEquals("bye",ar1[1]);
		assertEquals("1",ar1[2]);
		
		assertEquals(Ts(1992267947),ar2[0]);
		assertEquals(Ts(962276918),ar2[1]);
		assertEquals(Ts(1540999190),ar2[2]);
	}
	
	public void testMerge3() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "hello";
		ar1[1] = "bye";
		ar1[2] = "world";
		sort.merge(0,1,3,ar1,ar2);
		assertEquals("hello",ar1[0]);
		assertEquals("bye",ar1[1]);
		assertEquals("world",ar1[2]);
		
		assertEquals(Ts(1261806801),ar2[0]);
		assertEquals(Ts(2051405185),ar2[1]);
		assertEquals(Ts(316423297),ar2[2]);
	}
	
	public void testMerge4() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "hello";
		ar1[1] = "yellow";
		ar1[2] = "world";
		sort.merge(0,2,3,ar1,ar2);
		assertEquals("hello",ar1[0]);
		assertEquals("yellow",ar1[1]);
		assertEquals("world",ar1[2]);
		
		assertEquals("hello",ar2[0]);
		assertEquals("world",ar2[1]);
		assertEquals("yellow",ar2[2]);
	}
	
	public void testMerge5() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "hello";
		ar1[1] = "yellow";
		ar1[2] = "bye";
		ar1[3] = "world";
		sort.merge(0,2,4,ar1,ar2);
		assertEquals("hello",ar1[0]);
		assertEquals("yellow",ar1[1]);
		assertEquals("bye",ar1[2]);
		assertEquals("world",ar1[3]);
		
		assertEquals("bye",ar2[0]);
		assertEquals("hello",ar2[1]);
		assertEquals("world",ar2[2]);
		assertEquals("yellow",ar2[3]);
	}
	
	public void testMerge6() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "bye";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "yellow";

		sort.merge(0,2,4,ar1,ar2);
		assertEquals("bye",ar1[0]);
		assertEquals("hello",ar1[1]);
		assertEquals("world",ar1[2]);
		assertEquals("yellow",ar1[3]);

		assertEquals("bye",ar2[0]);
		assertEquals("hello",ar2[1]);
		assertEquals("world",ar2[2]);
		assertEquals("yellow",ar2[3]);
	}
	
	public void testMerge7() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "bye";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "yellow";
		ar2[0] = "0";
		ar2[1] = "1";
		ar2[2] = "2";
		ar2[3] = "3";
		sort.merge(2,4,4,ar1,ar2);
		assertEquals("bye",ar1[0]);
		assertEquals("hello",ar1[1]);
		assertEquals("world",ar1[2]);
		assertEquals("yellow",ar1[3]);
		
		assertEquals("0",ar2[0]);
		assertEquals("1",ar2[1]);
		assertEquals("world",ar2[2]);
		assertEquals("yellow",ar2[3]);			
	}

	public void testMerge8() {
		sort = new SortUtils<String>(backward); //NB: reverse!
		ar1[0] = "yellow";
		ar1[1] = "bye";
		ar1[2] = "yellow";
		ar1[3] = "world";
		ar2[0] = "0";
		ar2[1] = "1";
		ar2[2] = "2";
		ar2[3] = "3";
		sort.merge(0,2,4,ar1,ar2);

		assertEquals("yellow",ar1[0]);
		assertEquals("bye",ar1[1]);
		assertEquals("yellow",ar1[2]);
		assertEquals("world",ar1[3]);

		assertEquals("yellow",ar2[0]);
		assertEquals("yellow",ar2[1]);
		assertEquals("world",ar2[2]);
		assertEquals("bye",ar2[3]);
	}
	
	public void testMerge9() {
		sort = new SortUtils<String>(backward); //NB: reverse!
		ar1 = new String[8];
		ar2 = new String[8];
		
		ar1[0] = "of";
		ar1[2] = "making";
		ar1[1] = "many";
		ar1[3] = "books";
		ar1[4] = "there";
		ar1[6] = "is";
		ar1[5] = "no";
		ar1[7] = "end";
		
		sort.merge(0, 4, 8, ar1, ar2);
		
		assertEquals("of",ar2[1]);
		assertEquals("making",ar2[4]);
		assertEquals("many",ar2[3]);
		assertEquals("books",ar2[7]);
		assertEquals("there",ar2[0]);
		assertEquals("is",ar2[5]);
		assertEquals("no",ar2[2]);
		assertEquals("end",ar2[6]);
	}
	
	public void testMergeSortKeep0() {
		sort = new SortUtils<String>(backward);	
		ar1[1] = "1";
		ar1[2] = "hello";
		ar1[3] = "3";
		sort.mergeSortKeep(3, 3, ar1, ar2);
		assertEquals("1",ar1[1]);
		assertEquals("hello",ar1[2]);
		assertEquals("3",ar1[3]);
	}
	
	public void testMergeSortKeep1() {
		sort = new SortUtils<String>(backward);	
		ar1[1] = "1";
		ar1[2] = "hello";
		ar1[3] = "3";
		sort.mergeSortKeep(2, 3, ar1, ar2);
		assertEquals(Ts(657314817),ar1[1]);
		assertEquals(Ts(1570216978),ar1[2]);
		assertEquals(Ts(1968323373),ar1[3]);
	}
	
	public void testMergeSortKeep2() {
		sort = new SortUtils<String>(backward);		
		ar1[2] = "hello";
		ar1[3] = "world";
		ar1[4] = "zzz";
		sort.mergeSortKeep(2, 4, ar1, ar2); // NB: sorting is backward!
		assertEquals(Ts(696281731),ar1[2]);
		assertEquals(Ts(484860299),ar1[3]);
		assertEquals(Ts(519406695),ar1[4]);
	}
	
	public void testMergeSortKeep3() {
		sort = new SortUtils<String>(backward);	
		ar1[2] = "world";
		ar1[3] = "bye";
		ar1[4] = "hello";
		sort.mergeSortKeep(2, 5, ar1, ar2);
		assertEquals(Ts(2102488281),ar1[2]);
		assertEquals(Ts(38992702),ar1[3]);
		assertEquals(Ts(1571896528),ar1[4]);		
	}
	
	public void testMergeSortKeep4() {
		sort = new SortUtils<String>(backward);
		ar1[0] = "0";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "bye";
		ar1[4] = "yellow";
		sort.mergeSortKeep(1, 5, ar1, ar2);

		assertEquals("0",ar1[0]);
		assertEquals("yellow",ar1[1]);
		assertEquals("world",ar1[2]);
		assertEquals("hello",ar1[3]);
		assertEquals("bye",ar1[4]);		
	}
	
	public void testMergeSortKeep5() {
		sort = new SortUtils<String>(backward);
		ar1[0] = "hello";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "bye";
		ar1[4] = "yellow";
		sort.mergeSortKeep(0, 5, ar1, ar2);

		assertEquals("yellow",ar1[0]);
		assertEquals("world",ar1[1]);
		assertEquals("hello",ar1[2]);
		assertEquals("hello",ar1[3]);
		assertEquals("bye",ar1[4]);		
	}
	
	public void testMergeSortKeep9() {
		sort = new SortUtils<String>(forward); //NB: normal!
		ar1 = new String[8];
		ar2 = new String[8];
		
		ar1[0] = "of";
		ar1[2] = "making";
		ar1[1] = "many";
		ar1[3] = "books";
		ar1[4] = "there";
		ar1[6] = "is";
		ar1[5] = "no";
		ar1[7] = "end";
	
		sort.mergeSortKeep(0, 8, ar1, ar2);
		
		assertEquals("of",ar1[6]);
		assertEquals("making",ar1[3]);
		assertEquals("many",ar1[4]);
		assertEquals("books",ar1[0]);
		assertEquals("there",ar1[7]);
		assertEquals("is",ar1[2]);
		assertEquals("no",ar1[5]);
		assertEquals("end",ar1[1]);
	}
	
	public void testMergeSortMove0() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "11";
		ar1[2] = "hello";
		ar1[3] = "13";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		sort.mergeSortMove(3, 3, ar1, ar2);
		assertEquals("21",ar2[1]);
		assertEquals("22",ar2[2]);
		assertEquals("23",ar2[3]);
	}
	
	public void testMergeSortMove1() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "11";
		ar1[2] = "hello";
		ar1[3] = "13";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		sort.mergeSortMove(2, 3, ar1, ar2);
		assertEquals(Ts(316871352),ar2[1]);
		assertEquals(Ts(376638258),ar2[2]);
		assertEquals(Ts(1478588185),ar2[3]);
	}
	
	public void testMergeSortMove2() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "11";
		ar1[2] = "yellow";
		ar1[3] = "world";
		ar1[4] = "14";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		sort.mergeSortMove(2, 4, ar1, ar2);
		assertEquals("11",ar1[1]);
		assertEquals("14",ar1[4]);
		
		assertEquals(Ts(122074617),ar2[1]);
		assertEquals(Ts(1517604076),ar2[2]);
		assertEquals(Ts(1652517394),ar2[3]);
		assertEquals(Ts(818095889),ar2[4]);		
	}
	
	public void testMergeSortMove3() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "11";
		ar1[2] = "world";
		ar1[3] = "bye";
		ar1[4] = "hello";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		sort.mergeSortMove(2, 5, ar1, ar2);
		assertEquals("11",ar1[1]);
		
		assertEquals(Ts(470341094),ar2[1]);
		assertEquals(Ts(40987993),ar2[2]);
		assertEquals(Ts(288918302),ar2[3]);
		assertEquals(Ts(1082208295),ar2[4]);		
	}
	
	public void testMergeSortMove4() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "11";
		ar1[1] = "yellow";
		ar1[2] = "world";
		ar1[3] = "bye";
		ar1[4] = "hello";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		sort.mergeSortMove(1, 5, ar1, ar2);
		assertEquals("11",ar1[0]);
		
		assertEquals("bye",ar2[1]);
		assertEquals("hello",ar2[2]);
		assertEquals("world",ar2[3]);
		assertEquals("yellow",ar2[4]);				
	}
	
	public void testMergeSortMove5() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "hello";
		ar1[1] = "yellow";
		ar1[2] = "world";
		ar1[3] = "bye";
		ar1[4] = "hello";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		sort.mergeSortMove(0, 5, ar1, ar2);
				
		assertEquals("bye",ar2[0]);
		assertEquals("hello",ar2[1]);
		assertEquals("hello",ar2[2]);
		assertEquals("world",ar2[3]);
		assertEquals("yellow",ar2[4]);				
	}	
	
	public void testMergeSortMove9() {
		sort = new SortUtils<String>(backward); //NB: reverse!
		ar1 = new String[8];
		ar2 = new String[8];
		
		ar1[0] = "of";
		ar1[2] = "making";
		ar1[1] = "many";
		ar1[3] = "books";
		ar1[4] = "there";
		ar1[6] = "is";
		ar1[5] = "no";
		ar1[7] = "end";
		sort.mergeSortMove(0,8,ar1,ar2);
		
		assertEquals("of",ar2[1]);
		assertEquals("making",ar2[4]);
		assertEquals("many",ar2[3]);
		assertEquals("books",ar2[7]);
		assertEquals("there",ar2[0]);
		assertEquals("is",ar2[5]);
		assertEquals("no",ar2[2]);
		assertEquals("end",ar2[6]);		
	}
	
	public void testDifference0() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "11";
		ar1[2] = "12";
		ar2[1] = "21";
		ar2[2] = "22";
		ar3[1] = "31";
		ar3[2] = "32";
		
		assertEquals(Ti(1583692267),sort.difference(1, 1, 5, 5, ar1, ar2, ar3));
		
		assertEquals("11",ar1[1]);
		assertEquals("12",ar1[2]);
		assertEquals("21",ar2[1]);
		assertEquals("22",ar2[2]);
		assertEquals("31",ar3[1]);
		assertEquals("32",ar3[2]);
	}
	
	public void testDifference1() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "11";
		ar1[2] = "12";
		ar2[1] = "21";
		ar2[2] = "22";
		ar3[1] = "31";
		ar3[2] = "32";
		
		assertEquals(Ti(58299344),sort.difference(2, 2, 1, 3, ar1, ar2, ar3));
		
		assertEquals(Ts(923851347),ar1[1]);
		assertEquals(Ts(253117428),ar1[2]);
		assertEquals(Ts(384821089),ar2[1]);
		assertEquals(Ts(712810827),ar2[2]);
		assertEquals(Ts(446976525),ar3[1]);
		assertEquals(Ts(1515406170),ar3[2]);
	}
	
	public void testDifference2() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "bye";
		ar1[2] = "hello";
		ar2[1] = "21";
		ar2[2] = "22";
		ar3[1] = "31";
		ar3[2] = "32";
		
		assertEquals(Ti(1555864416),sort.difference(1, 3, 1, 3, ar1, ar2, ar3));
		
		assertEquals("bye",ar1[1]);
		assertEquals("hello",ar1[2]);
		assertEquals("21",ar2[1]);
		assertEquals("22",ar2[2]);
		assertEquals(Ts(553550378),ar3[1]);
		assertEquals(Ts(1956729499),ar3[2]);
	}
	
	public void testDifference3() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "bye";
		ar1[2] = "hello";
		ar2[1] = "hello";
		ar2[2] = "world";
		ar3[1] = "31";
		ar3[2] = "32";
		
		assertEquals(Ti(2064730322),sort.difference(1, 3, 1, 3, ar1, ar2, ar3));
		
		assertEquals("bye",ar1[1]);
		assertEquals("hello",ar1[2]);
		assertEquals("hello",ar2[1]);
		assertEquals("world",ar2[2]);
		assertEquals(Ts(1996194926),ar3[1]);
		assertEquals(Ts(79994656),ar3[2]);
	}

	public void testDifference4() {
		sort = new SortUtils<String>(forward);
		ar1[1] = "hello";
		ar1[2] = "world";
		ar2[1] = "bye";
		ar2[2] = "hello";
		ar3[1] = "31";
		ar3[2] = "32";
		
		assertEquals(Ti(951281465),sort.difference(1, 3, 1, 3, ar1, ar2, ar3));
		
		assertEquals("hello",ar1[1]);
		assertEquals("world",ar1[2]);
		assertEquals("bye",ar2[1]);
		assertEquals("hello",ar2[2]);
		assertEquals(Ts(1421076082),ar3[1]);
		assertEquals(Ts(727416776),ar3[2]);
	}
	
	public void testDifference5() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "bye";
		ar1[1] = "hello";
		ar1[2] = "hello";
		ar1[3] = "world";
		ar1[4] = "yellow";
		ar2[0] = "20";
		ar2[1] = "bye";
		ar2[2] = "hello";
		ar2[3] = "world";
		ar2[4] = "yellow";
		ar3[0] = "30";
		ar3[1] = "31";
		ar3[2] = "32";
		
		assertEquals(Ti(528270398),sort.difference(0,5,2,4,ar1,ar2,ar3));
		
		assertEquals("bye",ar3[0]);
		assertEquals("yellow",ar3[1]);
		assertEquals("32",ar3[2]);
	}
	
	public void testDifference6() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "bye";
		ar1[1] = "hello";
		ar1[2] = "hello";
		ar1[3] = "world";
		ar1[4] = "yellow";
		ar2[0] = "20";
		ar2[1] = "bye";
		ar2[2] = "hello";
		ar2[3] = "world";
		ar2[4] = "yellow";
		
		assertEquals(Ti(918193694),sort.difference(0,5,3,4,ar1,ar2,ar1));
		
		assertEquals(Ts(911557723),ar1[0]);
		assertEquals(Ts(883274250),ar1[1]);
		assertEquals(Ts(1402986557),ar1[2]);
		assertEquals(Ts(1521030474),ar1[3]);
		assertEquals(Ts(1867105428),ar1[4]);
	}
	
	public void testDifference7() {
		sort = new SortUtils<String>(forward);
		ar1[0] = "bye";
		ar1[1] = "hello";
		ar1[2] = "hello";
		ar1[3] = "world";
		ar1[4] = "yellow";
		ar2[0] = "hello";
		ar2[1] = "world";
		ar2[2] = "world";
		ar2[3] = "yellow";
		ar2[4] = "zzz";
		
		assertEquals(Ti(163251777),sort.difference(0,5,0,5,ar1,ar2,ar1));
		
		assertEquals("bye",ar1[0]);
		assertEquals("hello",ar1[1]);
		assertEquals("hello",ar1[2]);
		assertEquals("world",ar1[3]);
		assertEquals("yellow",ar1[4]);
	}
	
	public void testDifference8() {
		sort = new SortUtils<String>(TestUtil.<String>nondiscrimination());
		ar1[0] = "bye";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "hello";
		ar1[4] = "yellow";
		ar2[0] = "20";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		ar3[0] = "30";

		// NB: the comparator says that all strings are equal
		assertEquals(Ti(96369367),sort.difference(0,5,3,4,ar1,ar2,ar3));

		assertEquals("30",ar3[0]);
	}
	
	public void testDifference9() {
		sort = new SortUtils<String>(backward);
		ar1 = new String[8];
		ar2 = new String[8];

		ar1[0] = "there";
		ar1[1] = "ox";
		ar1[2] = "ox";
		ar1[3] = "many";
		ar1[4] = "making";
		ar1[5] = "ist";
		ar1[6] = "end";
		ar1[7] = "books";

		ar2[0] = "there";
		ar2[1] = "of";
		ar2[2] = "no";
		ar2[3] = "many";
		ar2[4] = "making";
		ar2[5] = "is";
		ar2[6] = "end";
		ar2[7] = "books";
		
		ar3[0] = "30";
		ar3[1] = "31";
		ar3[2] = "32";
		ar3[3] = "33";
		ar3[4] = "34";
		
		assertEquals(3,sort.difference(0, 8, 0, 8, ar1, ar2, ar3));
		
		assertEquals("ox",ar3[0]);
		assertEquals("ox",ar3[1]);
		assertEquals("ist",ar3[2]);
		assertEquals("33",ar3[3]);
	}
	
	public void testUniq0() {
		ar1[0] = "hello";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "world";
		ar1[4] = "world";
		
		ar2[0] = "20";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		
		assertEquals(Ti(171194622),sort.uniq(3, 3, ar1, ar2));
		
		assertEquals("20",ar2[0]);
		assertEquals("21",ar2[1]);
		assertEquals("22",ar2[2]);
		assertEquals("23",ar2[3]);
	}
	
	public void testUniq1() {
		ar1[0] = "hello";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "world";
		ar1[4] = "world";
		
		ar2[0] = "20";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		
		assertEquals(Ti(525131556),sort.uniq(2, 3, ar1, ar2));
		
		assertEquals(Ts(1459744104),ar2[0]);
		assertEquals(Ts(1656213988),ar2[1]);
		assertEquals(Ts(244532361),ar2[2]);
		assertEquals(Ts(1302745755),ar2[3]);
	}

	public void testUniq2() {
		ar1[0] = "hello";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "world";
		ar1[4] = "world";
		
		ar2[0] = "20";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		
		assertEquals(Ti(2023217326),sort.uniq(1, 3, ar1, ar2));
		
		assertEquals(Ts(123375250),ar2[0]);
		assertEquals(Ts(67053361),ar2[1]);
		assertEquals(Ts(915391395),ar2[2]);
		assertEquals(Ts(377991837),ar2[3]);
	}

	public void testUniq3() {
		ar1[0] = "hello";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "world";
		ar1[4] = "world";
		
		ar2[0] = "20";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		
		assertEquals(Ti(93365248),sort.uniq(0, 3, ar1, ar2));
		
		assertEquals(Ts(188265520),ar2[0]);
		assertEquals(Ts(319568936),ar2[1]);
		assertEquals(Ts(1091965944),ar2[2]);
		assertEquals("23",ar2[3]);
	}

	public void testUniq5() {
		ar1[0] = "hello";
		ar1[1] = "hello";
		ar1[2] = "world";
		ar1[3] = "world";
		ar1[4] = "world";
		
		ar2[0] = "20";
		ar2[1] = "21";
		ar2[2] = "22";
		ar2[3] = "23";
		ar2[4] = "24";
		
		assertEquals(Ti(1078557825),sort.uniq(0, 5, ar1, ar2));
		
		assertEquals("hello",ar2[0]);
		assertEquals("world",ar2[1]);
		assertEquals("22",ar2[2]);
		assertEquals("23",ar2[3]);
	}
	
	public void testUnknown1() {
		SortUtils<TestUtil.Unknown> util = new SortUtils<TestUtil.Unknown>(TestUtil.unknownComparator);
		TestUtil.Unknown[] a1, a2, a3;
		a1 = new TestUtil.Unknown[8];
		a2 = a1.clone();
		a3 = a2.clone();
		
		TestUtil.Unknown u1, u2, u3, u4, u5;
		u1 = new TestUtil.Unknown(1);
		u2 = new TestUtil.Unknown(2);
		u3 = new TestUtil.Unknown(3);
		u4 = new TestUtil.Unknown(4);
		u5 = new TestUtil.Unknown(5);
		
		a1[0] = u3;
		a1[1] = u2;
		a1[2] = u5;
		a1[3] = u1;
		a1[4] = u4;
		
		util.mergeSortMove(0,5,a1,a2);
		
		assertEquals(u1,a2[0]);
		assertEquals(u2,a2[1]);
		assertEquals(u3,a2[2]);
		assertEquals(u4,a2[3]);
		assertEquals(u5,a2[4]);
		
		a1[0] = new TestUtil.Unknown(0);
		a1[1] = u1;
		a1[2] = new TestUtil.Unknown(1);
		a1[3] = u5;
		a1[4] = new TestUtil.Unknown(6);
		
		assertEquals(2,util.difference(0,5,0,5,a1,a2,a3));
		
		assertEquals(0,a3[0].value());
		assertEquals(6,a3[1].value());
		
		a3[2] = a2[0];
		a3[3] = a2[1];
		a3[4] = a2[2];
		a3[5] = a2[3];
		a3[6] = a2[4];
		
		util.merge(0,2,7,a3,a1);

		assertEquals(0,a1[0].value());
		assertEquals(1,a1[1].value());
		assertEquals(2,a1[2].value());
		assertEquals(3,a1[3].value());
		assertEquals(4,a1[4].value());
		assertEquals(5,a1[5].value());
		assertEquals(6,a1[6].value());
		
		a1[1] = new TestUtil.Unknown(2);
		a1[4] = new TestUtil.Unknown(3);
		
		assertEquals(5,util.uniq(0,7,a1,a3));
		
		assertEquals(0,a3[0].value());
		assertEquals(2,a3[1].value());
		assertEquals(3,a3[2].value());
		assertEquals(5,a3[3].value());
		assertEquals(6,a3[4].value());
		assertEquals(4,a3[5].value());
		assertEquals(5,a3[6].value());
	}
}