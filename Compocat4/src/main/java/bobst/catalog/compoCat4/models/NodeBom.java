package bobst.catalog.compoCat4.models;

public class NodeBom {
	
	private String name;
	private String id;
	private String parentId;
	private String drawing;
	
	public NodeBom(String name, String id, String parentId, String drawing) {
		super();
		this.name = name;
		this.id = id;
		this.parentId = parentId;
		this.drawing = drawing;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDrawing() {
		return drawing;
	}

	public void setDrawing(String drawing) {
		this.drawing = drawing;
	}
	
	

}
