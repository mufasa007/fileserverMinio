package com.activeclub.fileserverminio.common.constants;

import com.google.common.collect.Maps;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PreviewConstant {

    // 预览文件处理

    /**
     * 图片
     */
    public static String GIF = "gif";
    public static String TIF = "tif";
    public static String JPE = "jpe";
    public static String JPG = "jpg";
    public static String JPEG = "jpeg";


    /**
     * 预览图片的格式
     */
    public static Set<String> PICTURE_SET = new HashSet<>();

    static {
        PICTURE_SET.add(GIF);
        PICTURE_SET.add(JPE);
        PICTURE_SET.add(JPG);
        PICTURE_SET.add(JPEG);
    }

    /**
     * txt格式
     */
    public static Set<String> JSON_SET = new HashSet<>();

    static {
        JSON_SET.add(GIF);
        JSON_SET.add(JPE);
        JSON_SET.add(JPG);
        JSON_SET.add(JPEG);
    }

    public static Map<String, String> contextType = Maps.newHashMapWithExpectedSize(400);

    static {
        contextType.put("*", "application/octet-stream");
        contextType.put("001", "application/x-001");
        contextType.put("301", "application/x-301");
        contextType.put("323", "text/h323");
        contextType.put("906", "application/x-906");
        contextType.put("907", "drawing/907");
        contextType.put("a11", "application/x-a11");
        contextType.put("acp", "audio/x-mei-aac");
        contextType.put("ai", "application/postscript");
        contextType.put("aif", "audio/aiff");
        contextType.put("aifc", "audio/aiff");
        contextType.put("aiff", "audio/aiff");
        contextType.put("anv", "application/x-anv");
        contextType.put("asa", "text/asa");
        contextType.put("asf", "video/x-ms-asf");
        contextType.put("asp", "text/asp");
        contextType.put("asx", "video/x-ms-asf");
        contextType.put("au", "audio/basic");
        contextType.put("avi", "video/avi");
        contextType.put("awf", "application/vnd.adobe.workflow");
        contextType.put("biz", "text/xml");
        contextType.put("bmp", "application/x-bmp");
        contextType.put("bot", "application/x-bot");
        contextType.put("c4t", "application/x-c4t");
        contextType.put("c90", "application/x-c90");
        contextType.put("cal", "application/x-cals");
        contextType.put("cat", "application/vnd.ms-pki.seccat");
        contextType.put("cdf", "application/x-netcdf");
        contextType.put("cdr", "application/x-cdr");
        contextType.put("cel", "application/x-cel");
        contextType.put("cer", "application/x-x509-ca-cert");
        contextType.put("cg4", "application/x-g4");
        contextType.put("cgm", "application/x-cgm");
        contextType.put("cit", "application/x-cit");
        contextType.put("class", "java/*");
        contextType.put("cml", "text/xml");
        contextType.put("cmp", "application/x-cmp");
        contextType.put("cmx", "application/x-cmx");
        contextType.put("cot", "application/x-cot");
        contextType.put("crl", "application/pkix-crl");
        contextType.put("crt", "application/x-x509-ca-cert");
        contextType.put("csi", "application/x-csi");
        contextType.put("css", "text/css");
        contextType.put("cut", "application/x-cut");
        contextType.put("dbf", "application/x-dbf");
        contextType.put("dbm", "application/x-dbm");
        contextType.put("dbx", "application/x-dbx");
        contextType.put("dcd", "text/xml");
        contextType.put("dcx", "application/x-dcx");
        contextType.put("der", "application/x-x509-ca-cert");
        contextType.put("dgn", "application/x-dgn");
        contextType.put("dib", "application/x-dib");
        contextType.put("dll", "application/x-msdownload");
        contextType.put("doc", "application/msword");
        contextType.put("dot", "application/msword");
        contextType.put("drw", "application/x-drw");
        contextType.put("dtd", "text/xml");
        contextType.put("dwf", "Model/vnd.dwf");
//        contextType.put("dwf", "application/x-dwf");
        contextType.put("dwg", "application/x-dwg");
        contextType.put("dxb", "application/x-dxb");
        contextType.put("dxf", "application/x-dxf");
        contextType.put("edn", "application/vnd.adobe.edn");
        contextType.put("emf", "application/x-emf");
        contextType.put("eml", "message/rfc822");
        contextType.put("ent", "text/xml");
        contextType.put("epi", "application/x-epi");
        contextType.put("eps", "application/postscript");
//        contextType.put("eps", "application/x-ps");
        contextType.put("etd", "application/x-ebx");
        contextType.put("exe", "application/x-msdownload");
        contextType.put("fax", "image/fax");
        contextType.put("fdf", "application/vnd.fdf");
        contextType.put("fif", "application/fractals");
        contextType.put("fo", "text/xml");
        contextType.put("frm", "application/x-frm");
        contextType.put("g4", "application/x-g4");
        contextType.put("gbr", "application/x-gbr");
        contextType.put("gcd", "application/x-gcd");
        contextType.put("gif", "image/gif");
        contextType.put("gl2", "application/x-gl2");
        contextType.put("gp4", "application/x-gp4");
        contextType.put("hgl", "application/x-hgl");
        contextType.put("hmr", "application/x-hmr");
        contextType.put("hpg", "application/x-hpgl");
        contextType.put("hpl", "application/x-hpl");
        contextType.put("hqx", "application/mac-binhex40");
        contextType.put("hrf", "application/x-hrf");
        contextType.put("hta", "application/hta");
        contextType.put("htc", "text/x-component");
        contextType.put("htm", "text/html");
        contextType.put("html", "text/html");
        contextType.put("htt", "text/webviewhtml");
        contextType.put("htx", "text/html");
        contextType.put("icb", "application/x-icb");
        contextType.put("ico", "image/x-icon");
//        contextType.put("ico", "application/x-ico");
        contextType.put("iff", "application/x-iff");
        contextType.put("ig4", "application/x-g4");
        contextType.put("igs", "application/x-igs");
        contextType.put("iii", "application/x-iphone");
        contextType.put("img", "application/x-img");
        contextType.put("ins", "application/x-internet-signup");
        contextType.put("isp", "application/x-internet-signup");
        contextType.put("IVF", "video/x-ivf");
        contextType.put("java", "java/*");
        contextType.put("jfif", "image/jpeg");
        contextType.put("jpe", "image/jpeg");
//        contextType.put("jpe", "application/x-jpe");
        contextType.put("jpeg", "image/jpeg");
        contextType.put("jpg", "image/jpeg");
//        contextType.put("jpg", "application/x-jpg");
        contextType.put("js", "application/x-javascript");
        contextType.put("jsp", "text/html");
        contextType.put("la1", "audio/x-liquid-file");
        contextType.put("lar", "application/x-laplayer-reg");
        contextType.put("latex", "application/x-latex");
        contextType.put("lavs", "audio/x-liquid-secure");
        contextType.put("lbm", "application/x-lbm");
        contextType.put("lmsff", "audio/x-la-lms");
        contextType.put("ls", "application/x-javascript");
        contextType.put("ltr", "application/x-ltr");
        contextType.put("m1v", "video/x-mpeg");
        contextType.put("m2v", "video/x-mpeg");
        contextType.put("m3u", "url");
        contextType.put("m4e", "video/mpeg4");
        contextType.put("mac", "application/x-mac");
        contextType.put("man", "application/x-troff-man");
        contextType.put("math", "text/xml");
        contextType.put("mdb", "application/msaccess");
//        contextType.put("mdb", "application/x-mdb");
        contextType.put("mfp", "application/x-shockwave-flash");
        contextType.put("mht", "message/rfc822");
        contextType.put("mhtml", "message/rfc822");
        contextType.put("mi", "application/x-mi");
        contextType.put("mid", "audio/mid");
        contextType.put("midi", "audio/mid");
        contextType.put("mil", "application/x-mil");
        contextType.put("mml", "text/xml");
        contextType.put("mnd", "audio/x-musicnet-download");
        contextType.put("mns", "audio/x-musicnet-stream");
        contextType.put("mocha", "application/x-javascript");
        contextType.put("movie", "video/x-sgi-movie");
        contextType.put("mp1", "audio/mp1");
        contextType.put("mp2", "audio/mp2");
        contextType.put("mp2v", "video/mpeg");
        contextType.put("mp3", "audio/mp3");
        contextType.put("mp4", "video/mpeg4");
        contextType.put("mpa", "video/x-mpg");
        contextType.put("mpd", "application/vnd.ms-project");
        contextType.put("mpe", "video/x-mpeg");
        contextType.put("mpeg", "video/mpg");
        contextType.put("mpg", "video/mpg");
        contextType.put("mpga", "audio/rn-mpeg");
        contextType.put("mpp", "application/vnd.ms-project");
        contextType.put("mps", "video/x-mpeg");
        contextType.put("mpt", "application/vnd.ms-project");
        contextType.put("mpv", "video/mpg");
        contextType.put("mpv2", "video/mpeg");
        contextType.put("mpw", "application/vnd.ms-project");
        contextType.put("mpx", "application/vnd.ms-project");
        contextType.put("mtx", "text/xml");
        contextType.put("mxp", "application/x-mmxp");
        contextType.put("net", "image/pnetvue");
        contextType.put("nrf", "application/x-nrf");
        contextType.put("nws", "message/rfc822");
        contextType.put("odc", "text/x-ms-odc");
        contextType.put("out", "application/x-out");
        contextType.put("p10", "application/pkcs10");
        contextType.put("p12", "application/x-pkcs12");
        contextType.put("p7b", "application/x-pkcs7-certificates");
        contextType.put("p7c", "application/pkcs7-mime");
        contextType.put("p7m", "application/pkcs7-mime");
        contextType.put("p7r", "application/x-pkcs7-certreqresp");
        contextType.put("p7s", "application/pkcs7-signature");
        contextType.put("pc5", "application/x-pc5");
        contextType.put("pci", "application/x-pci");
        contextType.put("pcl", "application/x-pcl");
        contextType.put("pcx", "application/x-pcx");
        contextType.put("pdf", "application/pdf");
        contextType.put("pdx", "application/vnd.adobe.pdx");
        contextType.put("pfx", "application/x-pkcs12");
        contextType.put("pgl", "application/x-pgl");
        contextType.put("pic", "application/x-pic");
        contextType.put("pko", "application/vnd.ms-pki.pko");
        contextType.put("pl", "application/x-perl");
        contextType.put("plg", "text/html");
        contextType.put("pls", "audio/scpls");
        contextType.put("plt", "application/x-plt");
        contextType.put("png", "image/png");
//        contextType.put("png", "application/x-png");
        contextType.put("pot", "application/vnd.ms-powerpoint");
        contextType.put("ppa", "application/vnd.ms-powerpoint");
        contextType.put("ppm", "application/x-ppm");
        contextType.put("pps", "application/vnd.ms-powerpoint");
        contextType.put("ppt", "application/vnd.ms-powerpoint");
//        contextType.put("ppt", "application/x-ppt");
        contextType.put("pr", "application/x-pr");
        contextType.put("prf", "application/pics-rules");
        contextType.put("prn", "application/x-prn");
        contextType.put("prt", "application/x-prt");
        contextType.put("ps", "application/postscript");
//        contextType.put("ps", "application/x-ps");
        contextType.put("ptn", "application/x-ptn");
        contextType.put("pwz", "application/vnd.ms-powerpoint");
        contextType.put("r3t", "text/vnd.rn-realtext3d");
        contextType.put("ra", "audio/vnd.rn-realaudio");
        contextType.put("ram", "audio/x-pn-realaudio");
        contextType.put("ras", "application/x-ras");
        contextType.put("rat", "application/rat-file");
        contextType.put("rdf", "text/xml");
        contextType.put("rec", "application/vnd.rn-recording");
        contextType.put("red", "application/x-red");
        contextType.put("rgb", "application/x-rgb");
        contextType.put("rjs", "application/vnd.rn-realsystem-rjs");
        contextType.put("rjt", "application/vnd.rn-realsystem-rjt");
        contextType.put("rlc", "application/x-rlc");
        contextType.put("rle", "application/x-rle");
        contextType.put("rm", "application/vnd.rn-realmedia");
        contextType.put("rmf", "application/vnd.adobe.rmf");
        contextType.put("rmi", "audio/mid");
        contextType.put("rmj", "application/vnd.rn-realsystem-rmj");
        contextType.put("rmm", "audio/x-pn-realaudio");
        contextType.put("rmp", "application/vnd.rn-rn_music_package");
        contextType.put("rms", "application/vnd.rn-realmedia-secure");
        contextType.put("rmvb", "application/vnd.rn-realmedia-vbr");
        contextType.put("rmx", "application/vnd.rn-realsystem-rmx");
        contextType.put("rnx", "application/vnd.rn-realplayer");
        contextType.put("rp", "image/vnd.rn-realpix");
        contextType.put("rpm", "audio/x-pn-realaudio-plugin");
        contextType.put("rsml", "application/vnd.rn-rsml");
        contextType.put("rt", "text/vnd.rn-realtext");
        contextType.put("rtf", "application/msword");
//        contextType.put("rtf", "application/x-rtf");
        contextType.put("rv", "video/vnd.rn-realvideo");
        contextType.put("sam", "application/x-sam");
        contextType.put("sat", "application/x-sat");
        contextType.put("sdp", "application/sdp");
        contextType.put("sdw", "application/x-sdw");
        contextType.put("sit", "application/x-stuffit");
        contextType.put("slb", "application/x-slb");
        contextType.put("sld", "application/x-sld");
        contextType.put("slk", "drawing/x-slk");
        contextType.put("smi", "application/smil");
        contextType.put("smil", "application/smil");
        contextType.put("smk", "application/x-smk");
        contextType.put("snd", "audio/basic");
        contextType.put("sol", "text/plain");
        contextType.put("sor", "text/plain");
        contextType.put("spc", "application/x-pkcs7-certificates");
        contextType.put("spl", "application/futuresplash");
        contextType.put("spp", "text/xml");
        contextType.put("ssm", "application/streamingmedia");
        contextType.put("sst", "application/vnd.ms-pki.certstore");
        contextType.put("stl", "application/vnd.ms-pki.stl");
        contextType.put("stm", "text/html");
        contextType.put("sty", "application/x-sty");
        contextType.put("svg", "text/xml");
        contextType.put("swf", "application/x-shockwave-flash");
        contextType.put("tdf", "application/x-tdf");
        contextType.put("tg4", "application/x-tg4");
        contextType.put("tga", "application/x-tga");
        contextType.put("tif", "image/tiff");
//        contextType.put("tif", "application/x-tif");
        contextType.put("tiff", "image/tiff");
        contextType.put("tld", "text/xml");
        contextType.put("top", "drawing/x-top");
        contextType.put("torrent", "application/x-bittorrent");
        contextType.put("tsd", "text/xml");
        contextType.put("txt", "text/plain");
        contextType.put("uin", "application/x-icq");
        contextType.put("uls", "text/iuls");
        contextType.put("vcf", "text/x-vcard");
        contextType.put("vda", "application/x-vda");
        contextType.put("vdx", "application/vnd.visio");
        contextType.put("vml", "text/xml");
        contextType.put("vpg", "application/x-vpeg005");
        contextType.put("vsd", "application/vnd.visio");
//        contextType.put("vsd", "application/x-vsd");
        contextType.put("vss", "application/vnd.visio");
        contextType.put("vst", "application/vnd.visio");
//        contextType.put("vst", "application/x-vst");
        contextType.put("vsw", "application/vnd.visio");
        contextType.put("vsx", "application/vnd.visio");
        contextType.put("vtx", "application/vnd.visio");
        contextType.put("vxml", "text/xml");
        contextType.put("wav", "audio/wav");
        contextType.put("wax", "audio/x-ms-wax");
        contextType.put("wb1", "application/x-wb1");
        contextType.put("wb2", "application/x-wb2");
        contextType.put("wb3", "application/x-wb3");
        contextType.put("wbmp", "image/vnd.wap.wbmp");
        contextType.put("wiz", "application/msword");
        contextType.put("wk3", "application/x-wk3");
        contextType.put("wk4", "application/x-wk4");
        contextType.put("wkq", "application/x-wkq");
        contextType.put("wks", "application/x-wks");
        contextType.put("wm", "video/x-ms-wm");
        contextType.put("wma", "audio/x-ms-wma");
        contextType.put("wmd", "application/x-ms-wmd");
        contextType.put("wmf", "application/x-wmf");
        contextType.put("wml", "text/vnd.wap.wml");
        contextType.put("wmv", "video/x-ms-wmv");
        contextType.put("wmx", "video/x-ms-wmx");
        contextType.put("wmz", "application/x-ms-wmz");
        contextType.put("wp6", "application/x-wp6");
        contextType.put("wpd", "application/x-wpd");
        contextType.put("wpg", "application/x-wpg");
        contextType.put("wpl", "application/vnd.ms-wpl");
        contextType.put("wq1", "application/x-wq1");
        contextType.put("wr1", "application/x-wr1");
        contextType.put("wri", "application/x-wri");
        contextType.put("wrk", "application/x-wrk");
        contextType.put("ws", "application/x-ws");
        contextType.put("ws2", "application/x-ws");
        contextType.put("wsc", "text/scriptlet");
        contextType.put("wsdl", "text/xml");
        contextType.put("wvx", "video/x-ms-wvx");
        contextType.put("xdp", "application/vnd.adobe.xdp");
        contextType.put("xdr", "text/xml");
        contextType.put("xfd", "application/vnd.adobe.xfd");
        contextType.put("xfdf", "application/vnd.adobe.xfdf");
        contextType.put("xhtml", "text/html");
        contextType.put("xls", "application/vnd.ms-excel");
//        contextType.put("xls", "application/x-xls");
        contextType.put("xlw", "application/x-xlw");
        contextType.put("xml", "text/xml");
        contextType.put("xpl", "audio/scpls");
        contextType.put("xq", "text/xml");
        contextType.put("xql", "text/xml");
        contextType.put("xquery", "text/xml");
        contextType.put("xsd", "text/xml");
        contextType.put("xsl", "text/xml");
        contextType.put("xslt", "text/xml");
        contextType.put("xwd", "application/x-xwd");
        contextType.put("x_b", "application/x-x_b");
        contextType.put("x_t", "application/x-x_t");
    }

}