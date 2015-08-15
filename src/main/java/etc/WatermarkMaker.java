package etc;

import dao.DocumentDao;


public class WatermarkMaker implements Runnable {


    public static final int WATERMARKING = 3000;
    private DocumentDao documentDao;
    private Long documentId;

    public WatermarkMaker(DocumentDao documentDao, Long documentId) {
        this.documentDao = documentDao;
        this.documentId = documentId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(WATERMARKING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        documentDao.saveWatermark(documentId);

    }
}
