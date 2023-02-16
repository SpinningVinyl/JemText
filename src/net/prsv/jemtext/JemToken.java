package net.prsv.jemtext;

// not a "real" class - I'm using it more like a C struct
public class JemToken {

    public enum Type {
        JT_TEXT,
        JT_LIST_ITEM,
        JT_BLOCKQUOTE,
        JT_PRE_BEGIN,
        JT_PRE_TEXT,
        JT_PRE_END,
        JT_PASSTHRU_BEGIN,
        JT_PASSTHRU_TEXT,
        JT_PASSTHRU_END,
        JT_HR,
        JT_HEADING,
        JT_LINK
    }

    public String text;
    public JemHeading heading = new JemHeading();
    public JemLink link = new JemLink();
    public Type type;


    public static class JemHeading {
        public String text;
        public int level;
        public int count = 0;
    }

    public static class JemLink {
        public String url;
        public String altText;
    }

}
