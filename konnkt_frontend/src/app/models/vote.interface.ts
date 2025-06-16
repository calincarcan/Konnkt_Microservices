export interface Vote {
    id: number;
    authorId: number;
    parentType: string; // post or comment
    parentId: number;
    voteType: number;
}