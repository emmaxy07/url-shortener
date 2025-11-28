import java.time.LocalDateTime;

public class UrlRecord {
    private final String longUrl;
    private final LocalDateTime createdAt;
    private int redirectCount;
    private LocalDateTime lastAccessedAt;
    private boolean isActive;

    public UrlRecord(String longUrl) {
        this.longUrl = longUrl;
        this.createdAt = LocalDateTime.now();
        this.redirectCount = 0;
        this.lastAccessedAt = null;
        this.isActive = true;
    }

    public String getlongUrl() {
        return longUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getRedirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(int redirectCount) {
        this.redirectCount = redirectCount;
    }

    public LocalDateTime getLastAccessedAt() {
        return lastAccessedAt;
    }

    public void setLastAccessedAt(LocalDateTime lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "UrlRecord [longUrl=" + longUrl + ", createdAt=" + createdAt + ", redirectCount=" + redirectCount
                + ", lastAccessedAt=" + lastAccessedAt + ", isActive=" + isActive + "]";
    }

}
