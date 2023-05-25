package com.livewallk.livewallpapersfork;

public class ForK_CategoryRVModal {

    private String category;
    private String forkImgUrl;

    public ForK_CategoryRVModal(String category, String forkImgUrl) {
        this.category = category;
        this.forkImgUrl = forkImgUrl;
    }

    // creating a constructor, getter and setter methods.
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getforkImgUrl() {
        return forkImgUrl;
    }

    public void setforkImgUrl(String forkImgUrl) {
        this.forkImgUrl = forkImgUrl;
    }
}