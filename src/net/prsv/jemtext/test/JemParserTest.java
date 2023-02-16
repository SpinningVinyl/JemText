package net.prsv.jemtext.test;

import net.prsv.jemtext.JemParser;

import static org.junit.Assert.*;

public class JemParserTest {
    private JemParser jemParser;

    @org.junit.Before
    public void setup() {
        jemParser = new JemParser();
    }

    @org.junit.Test
    public void html_list() throws Exception {
        String testInput = "* List Item 1\n" +
                "* List Item 2\n" +
                "\n" +
                "* List Item 3\n\n\n\n\n\n";
        String expectedOutput = "<ul>\n" +
                "<li>List Item 1</li>\n" +
                "<li>List Item 2</li>\n" +
                "<li>List Item 3</li>\n" +
                "</ul>\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html());
    }

    @org.junit.Test
    public void html_pre() throws Exception {
        String testInput = "```Pre examples\n" +
                "This is pre text\n" +
                "=> This should not be parsed as a link\n" +
                "# This should not be parsed as a header\n" +
                "* This should not be parsed as a list\n" +
                "===\n" +
                "Second line of pre text\n" +
                "```\n";
        String expectedOutput = "<!-- Pre examples -->\n" +
                "<pre>\n" +
                "This is pre text\n" +
                "=&gt; This should not be parsed as a link\n" +
                "# This should not be parsed as a header\n" +
                "* This should not be parsed as a list\n" +
                "===\n" +
                "Second line of pre text\n" +
                "</pre>\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput,jemParser.html());
    }

    @org.junit.Test
    public void html_passthru() throws Exception {
        String testInput = "+++\n" +
                "<h1>An example of HTML pass-through</h1>\n" +
                "<a href=\"https://example.org/test.php?q=a&p=b\">This is a link</a>\n" +
                "+++\n";
        String expectedOutput = "<!-- begin HTML pass-through -->\n" +
                "<h1>An example of HTML pass-through</h1>\n" +
                "<a href=\"https://example.org/test.php?q=a&p=b\">This is a link</a>\n" +
                "<!-- end HTML pass-through -->\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput,jemParser.html());
    }

    @org.junit.Test
    public void html_hr() throws Exception {
        String testInput = "===";
        String expectedOutput = "<hr />\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html());
    }

    @org.junit.Test
    public void html_linkAltText() throws Exception {
        String testInput = "=> https://example.org/ This is an example of a hyperlink";
        String expectedOutput = "<a class=\"jt-link\" href=\"https://example.org/\">This is an example of a hyperlink</a>\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html());
    }

    @org.junit.Test
    public void html_linkNoAltText() throws Exception {
        String testInput = "=> https://example.org/";
        String expectedOutput = "<a class=\"jt-link\" href=\"https://example.org/\">https://example.org/</a>\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html());
    }

    @org.junit.Test
    public void html_linkExpandedImageJpg() throws Exception {
        String testInput = "=> https://example.org/cat.jpg Alt text";
        String expectedOutput = "<img class=\"jt-image\" src=\"https://example.org/cat.jpg\" alt=\"Alt text\" title=\"Alt text\" />\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html(false, true, false));
    }

    @org.junit.Test
    public void html_linkExpandedImageGif() throws Exception {
        String testInput = "=> https://example.org/cat.gif Alt text";
        String expectedOutput = "<img class=\"jt-image\" src=\"https://example.org/cat.gif\" alt=\"Alt text\" title=\"Alt text\" />\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html(false, true, false));
    }

    @org.junit.Test
    public void html_linkExpandedImagePng() throws Exception {
        String testInput = "=> https://example.org/cat.png Alt text";
        String expectedOutput = "<img class=\"jt-image\" src=\"https://example.org/cat.png\" alt=\"Alt text\" title=\"Alt text\" />\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html(false, true, false));
    }

    @org.junit.Test
    public void html_linkExpandedImageJpgWithLink() throws Exception {
        String testInput = "=> https://example.org/cat.jpg Alt text";
        String expectedOutput = "<a href=\"https://example.org/cat.jpg\">\n" +
                "<img class=\"jt-image\" src=\"https://example.org/cat.jpg\" alt=\"Alt text\" title=\"Alt text\" />\n" +
                "</a>\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html(false, true, true));
    }

    @org.junit.Test
    public void html_headings() throws Exception {
        String testInput = "# Heading Level 1\n" +
                "## Heading Level 2\n" +
                "### Heading Level 3\n" +
                "# Another heading Level 1\n";
        String expectedOutput = "<h1 id=\"jt-heading-1\">Heading Level 1</h1>\n" +
                "<h2 id=\"jt-heading-2\">Heading Level 2</h2>\n" +
                "<h3 id=\"jt-heading-3\">Heading Level 3</h3>\n" +
                "<h1 id=\"jt-heading-4\">Another heading Level 1</h1>\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.html());
    }

    @org.junit.Test
    public void html_null() throws Exception {
        jemParser.parse(null);
        assertNull(jemParser.html());
    }

    @org.junit.Test
    public void title() throws Exception {
        String testInput = "First line of a Gemtext document\n" +
                "# Expected title\n" +
                "Another line of this document.\n" +
                "# Heading Level 1\n" +
                "## Heading Level 2\n" +
                "## Heading Level 3\n";
        jemParser.parse(testInput);
        assertEquals("Expected title", jemParser.title());
    }

    @org.junit.Test
    public void toc() throws Exception {
        String testInput = "# Heading Level 1\n" +
                "## Heading Level 2\n" +
                "### Heading Level 3\n" +
                "# Another heading Level 1\n";
        String expectedOutput = "<ul class=\"jt-toc-list\">\n" +
                "<li class=\"jt-toc-depth-1\"><a href=\"#jt-heading-1\">Heading Level 1</a></li>\n" +
                "<li class=\"jt-toc-depth-2\"><a href=\"#jt-heading-2\">Heading Level 2</a></li>\n" +
                "<li class=\"jt-toc-depth-3\"><a href=\"#jt-heading-3\">Heading Level 3</a></li>\n" +
                "<li class=\"jt-toc-depth-1\"><a href=\"#jt-heading-4\">Another heading Level 1</a></li>\n" +
                "</ul>";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.toc());
    }

    @org.junit.Test
    public void markdown_headings() throws Exception {
        String testInput = "# Heading Level 1\n" +
                "## Heading Level 2\n" +
                "### Heading Level 3\n" +
                "# Another heading Level 1\n";
        jemParser.parse(testInput);
        assertEquals(testInput, jemParser.markdown());
    }

    @org.junit.Test
    public void markdown_list() throws Exception {
        String testInput = "* List item 1\n" +
                "* List item 2\n" +
                "* List item 3\n";
        jemParser.parse(testInput);
        assertEquals(testInput, jemParser.markdown());
    }

    @org.junit.Test
    public void markdown_pre() throws Exception {
        String testInput = "```bash\n" +
                "youtube-dl -f 'bestvideo[ext=mp4]+bestaudio[ext=m4a]/bestvideo+bestaudio' --merge-output-format mp4 \"$@\"\n" +
                "```\n";
        jemParser.parse(testInput);
        assertEquals(testInput, jemParser.markdown());
    }

    @org.junit.Test
    public void markdown_linkNoAltText() throws Exception {
        String testInput = "=> https://prsv.net/\n";
        String expectedOutput = "[https://prsv.net/](https://prsv.net/)\n\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.markdown());
    }

    @org.junit.Test
    public void markdown_linkAltText() throws Exception {
        String testInput = "=> https://prsv.net/ My homepage\n";
        String expectedOutput = "[My homepage](https://prsv.net/)\n\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.markdown());
    }

    @org.junit.Test
    public void markdown_linkNoExpandedImage() throws Exception {
        String testInput = "=> https://prsv.net/logo.png\n";
        String expectedOutput = "[https://prsv.net/logo.png](https://prsv.net/logo.png)\n\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.markdown(false));
    }

    @org.junit.Test
    public void markdown_linkExpandedImage() throws Exception {
        String testInput = "=> https://prsv.net/logo.png\n";
        String expectedOutput = "![https://prsv.net/logo.png](https://prsv.net/logo.png)\n\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.markdown());
    }

    @org.junit.Test
    public void markdown_linkExpandedImageAltText() throws Exception {
        String testInput = "=> https://prsv.net/logo.png My beautiful logo\n";
        String expectedOutput = "![My beautiful logo](https://prsv.net/logo.png)\n\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.markdown());
    }

    @org.junit.Test
    public void markdown_hr() throws Exception {
        String testInput = "===\n";
        String expectedOutput = "---\n\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.markdown());
    }

    @org.junit.Test
    public void markdown_blockquote() throws Exception {
        String testInput = "> This is my beautiful >blockquote<\n";
        String expectedOutput = "> This is my beautiful &gt;blockquote&lt;\n";
        jemParser.parse(testInput);
        assertEquals(expectedOutput, jemParser.markdown());
    }

}