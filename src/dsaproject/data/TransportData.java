package dsaproject.data;

import dsaproject.model.Transport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransportData {
    public static List<Transport> getRoutes() {
        List<Transport> routes = new ArrayList<>();
        routes.add(r("101","Bus","Vijayawada - Guntur", new String[]{"Vijayawada","Benz Circle","Mangalagiri","Namburu","Guntur"}, new Integer[]{5,10,8,7}));
        routes.add(r("102","Bus","Vijayawada - Guntur via Tadepalli", new String[]{"Vijayawada","Undavalli","Tadepalli","Mangalagiri","Namburu","Guntur"}, new Integer[]{7,5,8,7,7}));
        routes.add(r("103","Bus","Vijayawada Railway Station - Guntur", new String[]{"Vijayawada Railway Station","Benz Circle","Mangalagiri","Namburu","Guntur Railway Station"}, new Integer[]{5,10,8,7}));
        routes.add(r("104","Bus","Guntur - Vijayawada Bus Stand", new String[]{"Guntur","Namburu","Mangalagiri","Benz Circle","Vijayawada Bus Stand"}, new Integer[]{7,8,10,5}));
        routes.add(r("105","Bus","Vijayawada - Amaravati", new String[]{"Vijayawada","Undavalli","Amaravati Road","Amaravati"}, new Integer[]{7,10,8}));
        routes.add(r("106","Bus","Guntur - Amaravati", new String[]{"Guntur","Namburu","Mangalagiri","Amaravati Road","Amaravati"}, new Integer[]{7,8,8,8}));
        routes.add(r("107","Train","Vijayawada Railway Station - Guntur Railway Station", new String[]{"Vijayawada Railway Station","Krishna Canal","Mangalagiri","Namburu","Guntur Railway Station"}, new Integer[]{8,12,12,18}));
        routes.add(r("108","Bus","Vijayawada - Tenali", new String[]{"Vijayawada","Benz Circle","Mangalagiri","Namburu","Tenali"}, new Integer[]{5,10,8,20}));
        routes.add(r("109","Bus","Guntur - Tenali", new String[]{"Guntur","Namburu","Tenali"}, new Integer[]{7,20}));
        routes.add(r("110","Bus","Vijayawada - Nuzvid", new String[]{"Vijayawada","Gannavaram","Hanuman Junction","Nuzvid"}, new Integer[]{12,15,12}));
        routes.add(r("111","Bus","Vijayawada - Gannavaram", new String[]{"Vijayawada","Ramavarappadu","Enikepadu","Gannavaram"}, new Integer[]{6,5,10}));
        routes.add(r("112","Bus","Vijayawada - Ibrahimpatnam", new String[]{"Vijayawada","Bhavanipuram","Ibrahimpatnam"}, new Integer[]{6,12}));
        routes.add(r("113","Bus","Vijayawada - Kondapalli", new String[]{"Vijayawada","Bhavanipuram","Kondapalli"}, new Integer[]{6,10}));
        routes.add(r("114","Bus","Guntur - Vijayawada via Amaravati", new String[]{"Guntur","Mangalagiri","Amaravati","Undavalli","Vijayawada"}, new Integer[]{8,8,10,7}));
        routes.add(r("115","Bus","Vijayawada Bus Stand - Guntur", new String[]{"Vijayawada Bus Stand","Benz Circle","Mangalagiri","Namburu","Guntur"}, new Integer[]{5,10,8,7}));
        routes.add(r("116","Train","Vijayawada Railway Station - Tenali Railway Station", new String[]{"Vijayawada Railway Station","Krishna Canal","Mangalagiri","Namburu","Tenali Railway Station"}, new Integer[]{8,12,12,25}));
        routes.add(r("117","Bus","Guntur - Vijayawada via Amaravati", new String[]{"Guntur","Mangalagiri","Amaravati","Undavalli","Vijayawada"}, new Integer[]{8,8,10,7}));
        routes.add(r("118","Bus","Vijayawada - Pedana", new String[]{"Vijayawada","Machavaram","Kankipadu","Pamarru","Pedana"}, new Integer[]{10,12,15,8}));
        routes.add(r("119","Bus","Vijayawada - Machilipatnam", new String[]{"Vijayawada","Kankipadu","Pamarru","Pedana","Machilipatnam"}, new Integer[]{12,15,8,15}));
        routes.add(r("120","Bus","Guntur - Chilakaluripet", new String[]{"Guntur","Nallapadu","Perecherla","Chilakaluripet"}, new Integer[]{5,15,30}));
        routes.add(r("121","Bus","Guntur - Narasaraopet", new String[]{"Guntur","Nallapadu","Sattenapalle","Narasaraopet"}, new Integer[]{5,25,15}));
        routes.add(r("122","Bus","Vijayawada - Eluru", new String[]{"Vijayawada","Gannavaram","Hanuman Junction","Denduluru","Eluru"}, new Integer[]{12,15,20,15}));
        routes.add(r("123","Bus","Vijayawada - Rajahmundry", new String[]{"Vijayawada","Hanuman Junction","Nuzvid","Eluru","Tadepalligudem","Rajahmundry"}, new Integer[]{25,20,20,55,65}));
        routes.add(r("124","Train","Vijayawada - Rajahmundry", new String[]{"Vijayawada Railway Station","Eluru Railway Station","Tadepalligudem Railway Station","Nidadavolu","Rajahmundry Railway Station"}, new Integer[]{60,55,35,40}));
        routes.add(r("125","Bus","Guntur - Vijayawada Express", new String[]{"Guntur","Mangalagiri","Benz Circle","Vijayawada"}, new Integer[]{8,10,5}));
        return routes;
    }

    private static Transport r(String no, String type, String name, String[] stops, Integer[] distances) {
        return new Transport(no, type, name, Arrays.asList(stops), Arrays.asList(distances));
    }
}
