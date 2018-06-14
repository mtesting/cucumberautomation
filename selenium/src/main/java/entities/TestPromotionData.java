package entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import other.SeleniumUtils;

public class TestPromotionData extends SeleniumUtils {

    private String name;
    private String title;
    private String subtitle;
    private String details;
    private String content;
    private String endDate;
    private String buttonLabel;
    private String backgroundLink;
    private String buttonLinkURL;

    public TestPromotionData(String promoType) throws DecoderConfigException {
        String timestamp = getDate("yyyyMMddHHmmss", 0);
        name = "promo-" + promoType + "-" + timestamp;
        title = "title-" + promoType + "-" + timestamp;
        subtitle = "subtitle-" + timestamp;
        details = "details-" + timestamp;
        content = "content" + timestamp;
        buttonLabel = "buttonLabel-" + timestamp;
        Decoder decoder = DecoderManager.getManager().getDecoder();
        backgroundLink = decoder.decodePunterUrl("MYBET") + "/live";
        buttonLinkURL = decoder.decodePunterUrl("MYBET") + "/casino";
        endDate = getDate("dd/MM/yyyy HH:mm", 1);
    }

    /**
     * Function to get current date with an specific format
     *
     * @param dateFormat date output format
     * @param sum optional param to sum days
     * @return custom date format
     */
    private String getDate(String dateFormat, int sum) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, sum);
        return sdf.format(c.getTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getBackgroundLink() {
        return backgroundLink;
    }

    public void setBackgroundLink(String backgroundLink) {
        this.backgroundLink = backgroundLink;
    }

    public String getButtonLinkURL() {
        return buttonLinkURL;
    }

    public void setButtonLinkURL(String buttonLinkURL) {
        this.buttonLinkURL = buttonLinkURL;
    }
}
