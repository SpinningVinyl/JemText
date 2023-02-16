package net.prsv.jemtext;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JemParser {

    private String title = null;

    private final ArrayList<JemToken> tokenStream = new ArrayList<>();

    private final Pattern LINK_PATTERN = Pattern.compile("^=>\\s*(\\S+)\\s*(.*)$");
    private final Pattern IMAGE_URL_PATTERN = Pattern.compile("^\\S*\\.(?i)(gif|jpeg|jpg|jfif|png)$");
    private final Pattern H1_PATTERN = Pattern.compile("^#\\s+(.*)$");
    private final Pattern H2_PATTERN = Pattern.compile("^##\\s+(.*)$");
    private final Pattern H3_PATTERN = Pattern.compile("^###\\s+(.*)$");
    private final Pattern HR_PATTERN = Pattern.compile("^===$");
    private final Pattern LI_PATTERN = Pattern.compile("^\\*\\s+(.*)$");
    private final Pattern PRE_PATTERN = Pattern.compile("^```(.*)$");
    private final Pattern PASSTHRU_PATTERN = Pattern.compile("^\\+\\+\\+$");
    private final Pattern BLOCKQUOTE_PATTERN = Pattern.compile("^>\\s*(.*)$");

    // converts input into a stream of unambiguous tokens
    // also sets the first level 1 heading as this.title
    public void parse(String input) {
        // resetting the state of the parser
        tokenStream.clear();
        title = null;

        // if input is null, don't attempt to parse
        if (input == null) return;

        String[] lines = input.split("\\R");

        boolean pre = false;
        boolean passthru = false;
        int headingCount = 0;

        for (String line : lines) {
            String strippedLine = line.strip();
            Matcher linkMatcher = LINK_PATTERN.matcher(strippedLine);
            Matcher h1Matcher = H1_PATTERN.matcher(strippedLine);
            Matcher h2Matcher = H2_PATTERN.matcher(strippedLine);
            Matcher h3Matcher = H3_PATTERN.matcher(strippedLine);
            Matcher hrMatcher = HR_PATTERN.matcher(strippedLine);
            Matcher liMatcher = LI_PATTERN.matcher(strippedLine);
            Matcher preMatcher = PRE_PATTERN.matcher(strippedLine);
            Matcher passthruMatcher = PASSTHRU_PATTERN.matcher(strippedLine);
            Matcher bqMatcher = BLOCKQUOTE_PATTERN.matcher(strippedLine);

            JemToken token = new JemToken();

            if(preMatcher.find()) {
                pre = !pre;
                if (pre) {
                    token.type = JemToken.Type.JT_PRE_BEGIN;
                    if (preMatcher.group(1).length() > 0)
                        token.text = preMatcher.group(1);
                }
                else
                    token.type = JemToken.Type.JT_PRE_END;
            } else if (passthruMatcher.find()) {
                passthru = !passthru;
                if (passthru)
                    token.type = JemToken.Type.JT_PASSTHRU_BEGIN;
                else
                    token.type = JemToken.Type.JT_PASSTHRU_END;
            } else if(pre) {
                token.type = JemToken.Type.JT_PRE_TEXT;
                token.text = line;
            } else if(passthru) {
                token.type = JemToken.Type.JT_PASSTHRU_TEXT;
                token.text = line;
            } else if (linkMatcher.find()) {
                token.type = JemToken.Type.JT_LINK;
                token.link.url = linkMatcher.group(1);
                token.link.altText = linkMatcher.group(2).length() > 0 ? linkMatcher.group(2) : token.link.url;
            } else if (h1Matcher.find()) {
                token.type = JemToken.Type.JT_HEADING;
                headingCount++;
                token.heading.count = headingCount;
                token.heading.level = 1;
                token.heading.text = h1Matcher.group(1);
                if (title == null) {
                    title = token.heading.text;
                }
            } else if (h2Matcher.find()) {
                token.type = JemToken.Type.JT_HEADING;
                headingCount++;
                token.heading.count = headingCount;
                token.heading.level = 2;
                token.heading.text = h2Matcher.group(1);
            } else if (h3Matcher.find()) {
                token.type = JemToken.Type.JT_HEADING;
                headingCount++;
                token.heading.count = headingCount;
                token.heading.level = 3;
                token.heading.text = h3Matcher.group(1);
            } else if (hrMatcher.find()) {
                token.type = JemToken.Type.JT_HR;
            } else if (liMatcher.find()) {
                token.type = JemToken.Type.JT_LIST_ITEM;
                token.text = liMatcher.group(1);
            } else if (bqMatcher.find()) {
                token.type = JemToken.Type.JT_BLOCKQUOTE;
                token.text = bqMatcher.group(1);
            } else {
                token.type = JemToken.Type.JT_TEXT;
                token.text = line;
            }
            tokenStream.add(token);
        }
    }

    // renders the token stream as HTML
    // returns NULL if the token stream is empty
    public String html(boolean strict, boolean expandImages, boolean linkImages) {
        if (tokenStream.isEmpty()) {
            return null;
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < tokenStream.size(); i++) {
            JemToken token = tokenStream.get(i);
            switch(token.type) {
                case JT_PRE_BEGIN:
                    if (null != token.text && token.text.length() > 0) {
                        output.append(String.format("<!-- %s -->\n", token.text));
                    }
                    output.append("<pre>\n");
                    break;
                case JT_PRE_END:
                    output.append("</pre>\n");
                    break;
                case JT_PASSTHRU_BEGIN:
                    if (!strict)
                        output.append("<!-- begin HTML pass-through -->\n");
                    break;
                case JT_PASSTHRU_END:
                    if (!strict)
                        output.append("<!-- end HTML pass-through -->\n");
                    break;
                case JT_HR:
                    if (!strict)
                        output.append("<hr />\n");
                    break;
                case JT_LINK:
                    Matcher m = IMAGE_URL_PATTERN.matcher(token.link.url);
                    if(expandImages && m.find()) {
                        if(linkImages) {
                            output.append(String.format("<a href=\"%s\">\n", JTUtils.htmlEncode(token.link.url)));
                        }
                        output.append(String.format("<img class=\"jt-image\" src=\"%s\" alt=\"%s\" title=\"%s\" />\n",
                                JTUtils.htmlEncode(token.link.url), JTUtils.htmlEncode(token.link.altText),
                                JTUtils.htmlEncode(token.link.altText)));
                        if(linkImages) {
                            output.append("</a>\n");
                        }
                    } else {
                        output.append(String.format("<a class=\"jt-link\" href=\"%s\">%s</a>\n",
                                JTUtils.htmlEncode(token.link.url), JTUtils.htmlEncode(token.link.altText)));
                    }
                    break;
                case JT_HEADING:
                    output.append(String.format("<h%d id=\"%s\">%s</h%d>\n",
                            token.heading.level, headingID(token.heading.count),
                            JTUtils.htmlEncode(token.heading.text), token.heading.level));
                    break;
                case JT_LIST_ITEM:
                    // if the previous token is not JT_LIST_ITEM, open the unordered list
                    int prevIndex = i - 1;
                    JemToken previousToken = null;
                    if (prevIndex >= 0) {
                        previousToken = tokenStream.get(prevIndex);
                    }
                    if(previousToken != null &&
                            previousToken.type == JemToken.Type.JT_TEXT &&
                            (previousToken.text == null || previousToken.text.equals(""))) {
                        prevIndex = i - 2;
                        if (prevIndex >= 0) {
                            previousToken = tokenStream.get(prevIndex);
                        } else {
                            previousToken = null;
                        }
                    }
                    if (previousToken == null || previousToken.type != JemToken.Type.JT_LIST_ITEM) {
                        output.append("<ul>\n");
                    }
                    output.append(String.format("<li>%s</li>\n", JTUtils.htmlEncode(token.text)));
                    // if the next token is not JT_LIST_ITEM, close the unordered list
                    int nextIndex = i + 1;
                    JemToken nextToken = null;
                    if(nextIndex <= tokenStream.size() - 1) {
                        nextToken = tokenStream.get(nextIndex);
                    }
                    if(nextToken != null &&
                            nextToken.type == JemToken.Type.JT_TEXT &&
                            (nextToken.text == null || nextToken.text.equals(""))) {
                        nextIndex = i + 2;
                        if(nextIndex <= tokenStream.size() - 1) {
                            nextToken = tokenStream.get(nextIndex);
                        } else {
                            nextToken = null;
                        }
                    }
                    if(nextToken == null || nextToken.type != JemToken.Type.JT_LIST_ITEM) {
                        output.append("</ul>\n");
                    }
                    break;
                case JT_BLOCKQUOTE:
                    output.append(String.format("<blockquote>%s</blockquote>\n", JTUtils.htmlEncode(token.text)));
                    break;
                case JT_PASSTHRU_TEXT:
                    if (!strict) {
                        output.append(token.text);
                        output.append('\n');
                    }
                    break;
                case JT_PRE_TEXT:
                    output.append(JTUtils.htmlEncode(token.text));
                    output.append('\n');
                    break;
                default:
                    if (token.text != null && !token.text.strip().equals(""))
                        output.append(String.format("<p>%s</p>\n", JTUtils.htmlEncode(token.text)));
            }
        }
        return output.toString();
    }

    public String html() {
        return html(false, true, true);
    }

    // renders TOC as HTML
    // returns NULL if the token stream is empty
    public String toc() {
        if(tokenStream.isEmpty()) {
            return null;
        }
        StringBuilder output = new StringBuilder();
        output.append("<ul class=\"jt-toc-list\">\n");

        tokenStream
                .stream()
                .filter(token -> token.type == JemToken.Type.JT_HEADING)
                .forEach(token -> output.append(createTocItem(token.heading.level,
                                            headingID(token.heading.count),
                                            JTUtils.htmlEncode(token.heading.text))));
        output.append("</ul>");
        return output.toString();
    }

    // renders the token stream as valid (strict) Gemtext
    // returns NULL if the token stream is empty
    public String gemini() {
        if(tokenStream.isEmpty()) {
            return null;
        }
        StringBuilder output = new StringBuilder();
        tokenStream
                .stream()
                .filter(token -> token.type != JemToken.Type.JT_PASSTHRU_TEXT
                        && token.type != JemToken.Type.JT_HR)
                .forEach(token -> {
                    switch(token.type) {
                        case JT_PRE_BEGIN:
                            output.append("```");
                            if (null != token.text && token.text.length() > 0)
                                output.append(token.text);
                            output.append('\n');
                            break;
                        case JT_PRE_END:
                            output.append("```\n");
                            break;
                        case JT_LINK:
                            output.append("=> ");
                            output.append(token.link.url);
                            if (!token.link.url.equals(token.link.altText)) {
                                output.append(" ");
                                output.append(token.link.altText);
                            }
                            output.append('\n');
                            break;
                        case JT_BLOCKQUOTE:
                            output.append("> ");
                            output.append(token.text);
                            output.append('\n');
                            break;
                        case JT_LIST_ITEM:
                            output.append("* ");
                            output.append(token.text);
                            output.append('\n');
                            break;
                        case JT_HEADING:
                            output.append("#".repeat(token.heading.level));
                            output.append(" ");
                            output.append(token.heading.text);
                            output.append('\n');
                        case JT_TEXT:
                        case JT_PRE_TEXT:
                            if (token.text != null) {
                                output.append(token.text);
                                output.append('\n');
                            }
                            break;
                    }
                });
        return output.toString();
    }

    // render the token stream as a Markdown document
    public String markdown(boolean expandImages) {
        if(tokenStream.isEmpty()) {
            return null;
        }
        StringBuilder output = new StringBuilder();
        tokenStream
                .forEach(token -> {
                    switch(token.type) {
                        case JT_PRE_BEGIN:
                            output.append("```");
                            if (null != token.text && token.text.length() > 0)
                                output.append(token.text);
                            output.append('\n');
                            break;
                        case JT_PRE_END:
                            output.append("```\n");
                            break;
                        case JT_LINK:
                            Matcher m = IMAGE_URL_PATTERN.matcher(token.link.url);
                            if(expandImages && m.find()) {
                                output.append(String.format("![%s](%s)\n\n", token.link.altText,
                                        token.link.url));
                            } else {
                                output.append(String.format("[%s](%s)\n\n", token.link.altText,
                                        token.link.url));
                            }
                            break;
                        case JT_BLOCKQUOTE:
                            output.append("> ");
                            output.append(JTUtils.htmlEncode(token.text));
                            output.append('\n');
                            break;
                        case JT_LIST_ITEM:
                            output.append("* ");
                            output.append(token.text);
                            output.append('\n');
                            break;
                        case JT_HEADING:
                            output.append("#".repeat(token.heading.level));
                            output.append(" ");
                            output.append(token.heading.text);
                            output.append('\n');
                        case JT_TEXT:
                            if (token.text != null) {
                                output.append(JTUtils.mdEncode(token.text));
                                output.append('\n');
                            }
                            break;
                        case JT_PRE_TEXT:
                            if (token.text != null) {
                                output.append(token.text.replace("`", "``"));
                                output.append('\n');
                            }
                            break;
                        case JT_PASSTHRU_TEXT:
                            if (token.text != null) {
                                output.append(token.text);
                                output.append('\n');
                            }
                        case JT_HR:
                            output.append("---\n\n");
                    }
                });
        return output.toString();
    }

    public String markdown() {
        return markdown(true);
    }

    // create a heading ID
    private String headingID(int headingCount) {
        return String.format("jt-heading-%x", headingCount);
    }

    // create a TOC element
    private String createTocItem(int headingLevel, String headingID, String text) {
        return String.format("<li class=\"jt-toc-depth-%d\"><a href=\"#%s\">%s</a></li>\n", headingLevel, headingID, text);
    }

    public String title() {
        return this.title; // may be NULL!
    }
}